/*
 * File: RequestService.java
 *
 * Description: Service class for handling business logic related to requests.
 *              Provides methods for creating, retrieving, updating, soft-deleting, and listing requests.
 *              Includes pagination and sorting for listing requests, with filtering for non-deleted records.
 *              Handles embedded lists (comments, attachmentDocuments, requestApprovals).
 */
package rw.ac.ilpd.notificationservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.notificationservice.mapper.RequestAttachmentDocumentMapper;
import rw.ac.ilpd.notificationservice.mapper.RequestCommentMapper;
import rw.ac.ilpd.notificationservice.mapper.RequestMapper;
import rw.ac.ilpd.notificationservice.mapper.UserRequestApprovalMapper;
import rw.ac.ilpd.notificationservice.model.nosql.document.Request;
import rw.ac.ilpd.notificationservice.repository.nosql.RequestRepository;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestCommentRequest;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestRequest;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestResponse;
import rw.ac.ilpd.sharedlibrary.dto.request.UserRequestApprovalRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService
{

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
//    private final NotificationTypeService notificationTypeService;
    private  final  RequestTypeService requestTypeService;
    private final RequestCommentMapper requestCommentMapper;
    private final RequestAttachmentDocumentMapper requestAttachmentDocumentMapper;
    private final UserRequestApprovalMapper userRequestApprovalMapper;

    /**
     * Creates a new request from the provided request DTO.
     *
     * @param request The request DTO
     * @return The created request response DTO
     * @throws EntityNotFoundException if the referenced request type is not found
     * @throws IllegalArgumentException          if the request is invalid
     */
    public RequestResponse createRequest(RequestRequest request)
    {
        log.info("Creating request with content: {}", request.getContent());

        // Validate that the referenced requestTypeId exists

//        notificationTypeService.getNotificationTypeById(request.getRequestTypeId());
        requestTypeService.getRequestTypeById(request.getRequestTypeId());

        Request requestEntity = requestMapper.toRequest(request);
        requestEntity.setIsDeleted(false); // Default for new requests
        Request savedRequest = requestRepository.save(requestEntity);
        log.debug("Request created successfully with ID: {}", savedRequest.getId());
        return requestMapper.fromRequest(savedRequest);
    }

    /**
     * Retrieves a request by its ID, excluding soft-deleted requests.
     *
     * @param id The ID of the request
     * @return The request response DTO
     * @throws EntityNotFoundException if the request is not found or is deleted
     */
    public RequestResponse getRequestById(String id)
    {
        log.info("Retrieving request with ID: {}", id);
        Request request = requestRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                {
                    log.error("Request with ID {} not found or is deleted", id);
                    return new EntityNotFoundException("Request with ID " + id + " not found or is deleted");
                });
        return requestMapper.fromRequest(request);
    }

    /**
     * Retrieves a paginated and sorted list of non-deleted requests.
     *
     * @param page    The page number (0-based)
     * @param size    The page size
     * @param sortBy  The field to sort by
     * @param sortDir The sort direction (asc or desc)
     * @return A page of request response DTOs
     */
    public Page<RequestResponse> getAllRequests(int page, int size, String sortBy, String sortDir)
    {
        log.info("Retrieving requests with page: {}, size: {}, sortBy: {}, sortDir: {}", page, size, sortBy, sortDir);
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Request> requests = requestRepository.findByIsDeletedFalse(pageable);
        return requests.map(requestMapper::fromRequest);
    }

    /**
     * Retrieves a paginated and sorted list of requests for a specific user.
     *
     * @param userId  The ID of the user who created the requests
     * @param page    The page number (0-based)
     * @param size    The page size
     * @param sortBy  The field to sort by
     * @param sortDir The sort direction (asc or desc)
     * @return A page of request response DTOs
     */
    public Page<RequestResponse> getRequestsByUserId(UUID userId, int page, int size, String sortBy, String sortDir)
    {
        log.info("Retrieving requests for user ID: {} with page: {}, size: {}, sortBy: {}, sortDir: {}",
                userId, page, size, sortBy, sortDir);
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Request> requests = requestRepository.findByCreatedByAndIsDeletedFalse(userId, pageable);
        return requests.map(requestMapper::fromRequest);
    }

    /**
     * Updates an existing request.
     *
     * @param id      The ID of the request to update
     * @param request The updated request DTO
     * @return The updated request response DTO
     * @throws EntityNotFoundException if the entity is not found or is deleted
     */
    public RequestResponse updateRequest(String id, RequestRequest request)
    {
        log.info("Updating request with ID: {}", id);
        Request existingRequest = requestRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                {
                    log.error("Request with ID {} not found or is deleted", id);
                    return new EntityNotFoundException("Request with ID " + id + " not found or is deleted");
                });

        // Validate that the referenced requestTypeId exists
        requestTypeService.getRequestTypeById(request.getRequestTypeId());

        existingRequest.setContent(request.getContent());
        existingRequest.setRequestTypeId(request.getRequestTypeId());
        existingRequest.setCreatedBy(request.getCreatedBy());
        existingRequest.setStatus(request.getStatus());
        existingRequest.setIntakeToId(request.getIntakeToId());
        existingRequest.setIntakeFromId(request.getIntakeFromId());
        existingRequest.setModuleId(request.getModuleId());
        existingRequest.setIntakeId(request.getIntakeId());

        existingRequest.setComments(request.getComments().stream()
                .map(requestCommentMapper::toRequestComment).toList());

        existingRequest.setAttachmentDocuments(request.getAttachmentDocuments().stream()
                .map(requestAttachmentDocumentMapper::toRequestAttachmentDocument).toList());

        existingRequest.setRequestApprovals(request.getUserApprovals().stream()
                .map(userRequestApprovalMapper::toUserRequestApproval).toList());

        Request updatedRequest = requestRepository.save(existingRequest);
        log.debug("Request updated successfully with ID: {}", updatedRequest.getId());
        return requestMapper.fromRequest(updatedRequest);
    }

    /**
     * Soft-deletes a request by setting isDeleted to true.
     *
     * @param id The ID of the request to delete
     * @throws EntityNotFoundException if the request is not found or is already deleted
     */
    public void deleteRequest(String id)
    {
        log.info("Soft-deleting request with ID: {}", id);
        Request request = requestRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                {
                    log.error("Request with ID {} not found or is already deleted", id);
                    return new EntityNotFoundException("Request with ID " + id + " not found or is already deleted");
                });
        request.setIsDeleted(true);
        requestRepository.save(request);
        log.debug("Request soft-deleted successfully with ID: {}", id);
    }

    /**
     * Adds a comment to a request
     *
     * @param id The ID of the request to add a comment to
     * @param comment The comment made by a user on the request
     * @throws EntityNotFoundException if the request is not found or is already deleted
     * @return RequestResponse An intance of the request with the new comment
     */
    public RequestResponse addComment(String id, @Valid RequestCommentRequest comment)
    {
        log.info("Adding comment: {} to request with ID: {}", comment, id);

        Request request = requestRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
        {
            log.error("Request to add comment to with ID {} not found", id);
            return new EntityNotFoundException("Request to add comment to with ID " + id + " not found");
        });

        // adding the new comment to the list of already existing comments
        request.getComments().add(requestCommentMapper.toRequestComment(comment));

        Request savedRequest = requestRepository.save(request);
        log.debug("Comment added to request successfully: {}", savedRequest);
        return requestMapper.fromRequest(savedRequest);
    }

    /**
     * Approve or reject a request
     *
     * @param id The ID of the request to add a comment to
     * @param userRequestApprovalRequest The object that contains data
     * @throws EntityNotFoundException if the request is not found or is already deleted
     * @return RequestResponse An intance of the request with the new comment
     */
    public RequestResponse approveRejectRequest(String id, @Valid UserRequestApprovalRequest
            userRequestApprovalRequest)
    {
        log.info("Approving request with user approval: {}", userRequestApprovalRequest);

        Request request = requestRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                {
                    log.error("Request with ID {} not found", id);
                    return new EntityNotFoundException("Request with ID " + id + " not found");
                });

        // adding the new approval or rejection to a request
        request.getRequestApprovals().add(userRequestApprovalMapper.toUserRequestApproval(userRequestApprovalRequest));

        Request savedRequest = requestRepository.save(request);
        log.debug("User approval added to request successfully: {}", savedRequest);
        return requestMapper.fromRequest(savedRequest);
    }

    void deleteWhereCreatedByIs(UUID id){
        requestRepository.deleteAllByCreatedBy(id);
    }

    void deleteWhereModuleIdIs(UUID id){
        log.debug("********* notification Called Delete module");
        requestRepository.deleteAllByModuleId(id);
    }
}