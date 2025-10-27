package rw.ac.ilpd.notificationservice.service;
import io.minio.*;
import io.minio.messages.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.notificationservice.exception.RemoteDependencyException;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectStorageRequest;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.BucketDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.BucketStatisticsResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.ObjectDetailResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioService {
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;
    @Value("${minio.client-access.end-point}")
    private String clientAccessEndPoint;

    public String uploadObject(ObjectStorageRequest objProps) throws Exception {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        String objectName = validateDocumentName(objProps.getAttachedFile(), objProps.getObjectPath().toLowerCase());
        ObjectWriteResponse minioObj=minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(objProps.getAttachedFile().getInputStream(), objProps.getAttachedFile().getSize(), -1)
                        .contentType(objProps.getAttachedFile().getContentType())
                        .build());
        return "/"+minioObj.bucket()+"/"+minioObj.object();
    }

    private static @NotNull String validateDocumentName(MultipartFile documentRequest, String path) {
        String originalFileName = documentRequest.getResource().getFilename();

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new IllegalArgumentException("Invalid file name.");
        }

        // Clean invalid characters
        originalFileName = originalFileName.replaceAll("[\\s/&?%#<>:\"|*\\\\]", "_");

        String extension = "";
        int dotIndex = originalFileName.lastIndexOf(".");
        if (dotIndex != -1) {
            extension = originalFileName.substring(dotIndex);
            originalFileName = originalFileName.substring(0, dotIndex); // Remove extension from base name
        }

        // Truncate base name if too long
        if (originalFileName.length() > 30) {
            originalFileName = originalFileName.substring(0, 30);
        }

        // Reattach extension
        originalFileName = originalFileName.concat(extension);

        // Add timestamp and path
        String newFileName = System.currentTimeMillis() + "_" + originalFileName;
        LocalDate localDate=LocalDate.now();
        // Add year/month
        path=path+"/"+localDate.getYear()+"/"+localDate.getMonth().toString().toLowerCase();
        return path.endsWith("/") ? path + newFileName : path + "/" + newFileName;
    }


    public InputStream downloadFile(String objectName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build());
    }
    public String softDelete(String objectName) throws Exception {
        objectName=objectName.split(clientAccessEndPoint+"/"+bucketName+"/")[1];
        // Get everything before the last slash (if any)
        String newObjectName = getNewPathHavingArchivePathUrl(objectName);
        // 1️⃣ Copy the object to the new location
        return moveObjectToAnotherLocation(objectName,newObjectName);
    }
    public String softUndoDelete(String objectName) throws Exception {
        objectName=objectName.split(clientAccessEndPoint+"/"+bucketName+"/")[1];
        // Get everything before the last slash (if any)
        String newObjectName=objectName ;
        int lastSlashIndex = objectName.lastIndexOf('/');
        int archiveIndex = objectName.lastIndexOf("/archive/");
        if (archiveIndex != -1 && archiveIndex < lastSlashIndex) {
            newObjectName = newObjectName.substring(0, archiveIndex) +
                    newObjectName.substring(archiveIndex + "/archive".length());
        }
        return moveObjectToAnotherLocation(objectName, newObjectName);

    }
//    @Async
    public void deleteObject(String objectName){
      try {
          log.info("Delete object from minio{}",objectName);
          minioClient.removeObject(
                  RemoveObjectArgs.builder()
                          .bucket(bucketName)
                          .object(objectName)
                          .build());
      }catch (Exception e){
          log.info(e.getMessage());
      }
    }
    /**
     * Deletes all objects inside a given MinIO "folder" and removes the folder placeholder itself if it exists.
     *
     * @param folderPath the folder path inside the bucket (e.g., "user-uploads/profile-images/")
     * @throws Exception if deletion fails
     */
    public void deleteBucket(String folderPath) throws Exception {
        // Ensure folder path ends with "/"
        if (!folderPath.endsWith("/")) {
            folderPath += "/";
        }

        log.info("Deleting folder '{}' from bucket '{}'", folderPath, bucketName);

        // 1️⃣ List all objects starting with this folder path
        Iterable<Result<Item>> objects = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(folderPath)
                        .recursive(true) // include subfolder contents
                        .build()
        );

        // 2️⃣ Delete each file inside the folder
        for (Result<Item> result : objects) {
            String objectName = result.get().objectName();
            log.debug("Deleting object: {}", objectName);
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        }

        // 3️⃣ Remove the "folder placeholder" if it exists (e.g., "my-folder/")
        try {
            log.debug("Deleting folder placeholder: {}", folderPath);
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(folderPath) // This is the empty placeholder key
                            .build()
            );
        } catch (Exception e) {
            log.debug("No folder placeholder '{}' found to delete", folderPath);
        }

        log.info("Folder '{}' deleted completely from bucket '{}'", folderPath, bucketName);
    }
    /**
     * Retrieves details about a folder in MinIO.
     *
     * @param folderPath the folder prefix (e.g., "user-uploads/profile-images/")
     * @return FolderInfo containing statistics
     * @throws Exception if listing fails
     */
    public BucketDetailResponse getBucketDetails(String folderPath) throws Exception {
        if (!folderPath.endsWith("/")) {
            folderPath += "/";
        }

        log.info("Fetching details for folder '{}' in bucket '{}'", folderPath, bucketName);

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(folderPath)
                        .recursive(true)
                        .build()
        );

        int fileCount = 0;
        long totalSize = 0;
        ZonedDateTime lastModified = null;

        for (Result<Item> result : results) {
            Item item = result.get();

            if (!item.isDir()) { // skip folder placeholders
                fileCount++;
                totalSize += item.size();

                // Track latest modification date
                if (lastModified == null || item.lastModified().isAfter(lastModified)) {
                    lastModified = item.lastModified();
                }
            }
        }

        return new BucketDetailResponse(folderPath, fileCount, totalSize, lastModified);
    }
    public List<ObjectDetailResponse> getAllObjectWithInBucket(String folderPath) throws Exception {
        if (!folderPath.endsWith("/")) {
            folderPath += "/";
        }

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(folderPath)
                        .recursive(true)
                        .build()
        );

        List<ObjectDetailResponse> files = new ArrayList<>();

        for (Result<Item> result : results) {
            Item item = result.get();
            // Skip folder placeholders (zero size and ends with '/')
            if (!item.isDir()) {
                files.add(new ObjectDetailResponse(
                        item.objectName(),
                        item.size(),
                        item.lastModified().toString()
                ));
            }
        }

        return files;
    }
    // Method to calculate folder stats
    public BucketStatisticsResponse calculateBucketStats(String folderPath) throws Exception {
        if (!folderPath.endsWith("/")) {
            folderPath += "/";
        }

        Iterable<Result<Item>> objects = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(folderPath)
                        .recursive(true)
                        .build()
        );

        long totalSize = 0;
        int count = 0;
        Instant lastModified = Instant.EPOCH;

        for (Result<Item> result : objects) {
            Item item = result.get();
            if (!item.isDir()) {
                count++;
                totalSize += item.size();
                if (item.lastModified().toInstant().isAfter(lastModified)) {
                    lastModified = item.lastModified().toInstant();
                }
            }
        }

        return new BucketStatisticsResponse(folderPath, totalSize, count, lastModified);
    }

    public void createBucket(@NotBlank(message = "Path to store document of this type is required") @Pattern(
            regexp = "^[a-zA-Z0-9/_-]+$",
            message = "Only letters, digits, underscores (_), hyphens (-), and forward slashes (/) are allowed"
    ) String path) {
       try {
           minioClient.putObject(
                   PutObjectArgs.builder()
                           .bucket(bucketName)
                           .object(path.endsWith("/") ? path : path + "/")
                           .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                           .build()
           );

       }catch (Exception e){
           log.error("Error making bucket {}",path,e);
           throw new RemoteDependencyException("Something went wrong from file server");
       }
    }

//    Helper Minio
private String moveObjectToAnotherLocation(String actualObjectName,String newObjectName) throws Exception {
    // 1️⃣ Copy the object to the new location
    ObjectWriteResponse minioObj=minioClient.copyObject(
            CopyObjectArgs.builder()
                    .bucket(bucketName)
                    .object(actualObjectName)
                    .source(CopySource.builder()
                            .bucket(bucketName)
                            .object(newObjectName)
                            .build())
                    .build()
    );

    // 2️⃣ Delete the original object
    minioClient.removeObject(
            RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(actualObjectName)
                    .build()
    );
    return clientAccessEndPoint +"/"+minioObj.bucket()+"/"+minioObj.object();
}
    private static @NotNull String getNewPathHavingArchivePathUrl(String objectName) {
        String objectPastPath = objectName.lastIndexOf("/") == -1
                ? ""
                : objectName.substring(0, objectName.lastIndexOf("/"));

        // Insert "archive/" after the last slash
        String newObjectName;
        if (objectPastPath.isEmpty()) {
            // No slash found, just prepend archive/
            newObjectName = "archive/" + objectName;
        } else {
            // Keep path before last slash, add /archive/, then filename
            String fileName = objectName.substring(objectName.lastIndexOf("/") + 1);
            newObjectName = objectPastPath + "/archive/" + fileName;
        }
        return newObjectName;
    }
}
