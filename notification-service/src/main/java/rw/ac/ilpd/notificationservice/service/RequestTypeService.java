/*
 * File: RequestTypeService.java
 *
 * Description: Service class for handling business logic related to request types.
 *              Provides methods for creating, retrieving, updating, soft-deleting, listing request types,
 *              and adding roles to a request type. Includes pagination and sorting for listing request types,
 *              with filtering for non-deleted records.
 */
package rw.ac.ilpd.notificationservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.notificationservice.mapper.RequestTypeMapper;
import rw.ac.ilpd.notificationservice.mapper.RequestTypeRoleMapper;
import rw.ac.ilpd.notificationservice.model.nosql.document.RequestType;
import rw.ac.ilpd.notificationservice.repository.nosql.RequestTypeRepository;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestTypeRoleRequest;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestTypeService
{
    private final RequestTypeRepository requestTypeRepository;
    private final RequestTypeMapper requestTypeMapper;
    private final RequestTypeRoleMapper requestTypeRoleMapper;

    /**
     * Creates a new request type from the provided request DTO.
     *
     * @param request The request type DTO
     * @return The created request type response DTO
     * @throws IllegalArgumentException if the request is invalid
     */
    public RequestTypeResponse createRequestType(RequestTypeRequest request)
    {
        log.info("Creating request type with name: {}", request.getName());

        RequestType requestType = requestTypeMapper.toRequestType(request);
        requestType.setDeletedStatus(false); // Default for new request types

        RequestType savedRequestType = requestTypeRepository.save(requestType);
        log.debug("Request type created successfully with ID: {}", savedRequestType.getId());

        return requestTypeMapper.fromRequestType(savedRequestType);
    }

    /**
     * Retrieves a request type by its ID, excluding soft-deleted request types.
     *
     * @param id The ID of the request type
     * @return The request type response DTO
     * @throws EntityNotFoundException if the request type is not found or is deleted
     */
    public RequestTypeResponse getRequestTypeById(String id)
    {
        log.info("Retrieving request type with ID: {}", id);
        RequestType requestType = requestTypeRepository.findByIdAndDeletedStatusFalse(id)
                .orElseThrow(() ->
                {
                    log.error("Request type with ID {} not found or is deleted", id);
                    return new EntityNotFoundException("Request type with ID " + id + " not found or is deleted");
                });
        return requestTypeMapper.fromRequestType(requestType);
    }

    /**
     * Retrieves a paginated and sorted list of non-deleted request types.
     *
     * @param page    The page number (0-based)
     * @param size    The page size
     * @param sortBy  The field to sort by
     * @param sortDir The sort direction (asc or desc)
     * @return A page of request type response DTOs
     */
    public Page<RequestTypeResponse> getAllRequestTypes(int page, int size, String sortBy, String sortDir)
    {
        log.info("Retrieving request types with page: {}, size: {}, sortBy: {}, sortDir: {}"
                , page, size, sortBy, sortDir);

        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RequestType> requestTypes = requestTypeRepository.findByDeletedStatusFalse(pageable);
        return requestTypes.map(requestTypeMapper::fromRequestType);
    }

    /**
     * Updates an existing request type.
     *
     * @param id      The ID of the request type to update
     * @param request The updated request type DTO
     * @return The updated request type response DTO
     * @throws EntityNotFoundException if the request type is not found or is deleted
     */
    public RequestTypeResponse updateRequestType(String id, RequestTypeRequest request)
    {
        log.info("Updating request type with ID: {}", id);
        RequestType existingRequestType = requestTypeRepository.findByIdAndDeletedStatusFalse(id)
                .orElseThrow(() ->
                {
                    log.error("Request type with ID {} not found or is deleted", id);
                    return new EntityNotFoundException("Request type with ID " + id + " not found or is deleted");
                });

        existingRequestType.setName(request.getName());
        existingRequestType.setNeedsPayment(request.getNeedsPayment());
        existingRequestType.setRoles(request.getRoles().stream()
                .map(requestTypeRoleMapper::toRequestTypeRole).toList());

        RequestType updatedRequestType = requestTypeRepository.save(existingRequestType);
        log.debug("Request type updated successfully with ID: {}", updatedRequestType.getId());
        return requestTypeMapper.fromRequestType(updatedRequestType);
    }

    /**
     * Soft-deletes a request type by setting deletedStatus to true.
     *
     * @param id The ID of the request type to delete
     * @throws EntityNotFoundException if the request type is not found or is already deleted
     */
    public void deleteRequestType(String id)
    {
        log.info("Soft-deleting request type with ID: {}", id);
        RequestType requestType = requestTypeRepository.findByIdAndDeletedStatusFalse(id)
                .orElseThrow(() ->
                {
                    log.error("Request type with ID {} not found or is already deleted", id);
                    return new EntityNotFoundException("Request type with ID " + id + " not found or is already deleted");
                });

        requestType.setDeletedStatus(true);
        requestTypeRepository.save(requestType);
        log.debug("Request type soft-deleted successfully with ID: {}", id);
    }

    /**
     * Adds a new role to an existing request type.
     *
     * @param requestTypeId The ID of the request type
     * @param roleRequest   The role to add
     * @return The updated request type response DTO
     * @throws EntityNotFoundException if the request type is not found or is deleted
     */
    public RequestTypeResponse addRoleToRequestType(String requestTypeId, RequestTypeRoleRequest roleRequest)
    {
        log.info("Adding role to request type with ID: {}", requestTypeId);
        RequestType requestType = requestTypeRepository.findByIdAndDeletedStatusFalse(requestTypeId)
                .orElseThrow(() ->
                {
                    log.error("Request type with ID {} not found or is deleted", requestTypeId);
                    return new EntityNotFoundException("Request type with ID " + requestTypeId + " not found or is deleted");
                });

        if (requestType.getRoles() == null)
        {
            requestType.setRoles(new ArrayList<>());
        }
        requestType.getRoles().add(requestTypeRoleMapper.toRequestTypeRole(roleRequest));
        RequestType updatedRequestType = requestTypeRepository.save(requestType);
        log.debug("Role added successfully to request type with ID: {}", updatedRequestType.getId());
        return requestTypeMapper.fromRequestType(updatedRequestType);
    }
}