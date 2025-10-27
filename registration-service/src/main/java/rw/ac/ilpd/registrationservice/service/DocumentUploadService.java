package rw.ac.ilpd.registrationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import com.netflix.discovery.EurekaClient;
import com.netflix.appinfo.InstanceInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Service
public class DocumentUploadService {

    @Autowired
    private EurekaClient eurekaClient;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DocumentResponse uploadDocument(MultipartFile file, String bucketName, String objectPath, String token) throws IOException {
        // Get notification service URL from Eureka
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("NOTIFICATION-SERVICE", false);
        String baseUrl = instance.getHomePageUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", token);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Create a ByteArrayResource from the MultipartFile
        ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        body.add("attachedFile", fileResource);
        body.add("bucketName", bucketName);
        body.add("objectPath", objectPath);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<DocumentResponse> response = restTemplate.exchange(
                baseUrl + "/documents",
                HttpMethod.POST,
                requestEntity,
                DocumentResponse.class
        );

        return response.getBody();
    }

    public List<DocumentResponse> uploadMultipleDocuments(List<MultipartFile> files, String bucketName, String objectPath, String token) throws IOException {
        // Get notification service URL from Eureka
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("NOTIFICATION-SERVICE", false);
        String baseUrl = instance.getHomePageUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", token);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Add each file to the request
        for (MultipartFile file : files) {
            ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
            body.add("attachedFiles", fileResource);
        }

        body.add("bucketName", bucketName);
        body.add("objectPath", objectPath);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/documents/upload-object-list",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Parse the JSON response to List<DocumentResponse>

        return objectMapper.readValue(
                response.getBody(),
                new TypeReference<List<DocumentResponse>>() {}
        );
    }
}