package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.handler.BulkResponseException;
import rw.ac.ilpd.academicservice.mapper.IntakeApplicationRequiredDocMapper;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.IntakeApplicationRequiredDoc;
import rw.ac.ilpd.academicservice.model.sql.IntakeApplicationRequiredDocName;
import rw.ac.ilpd.academicservice.repository.sql.IntakeApplicationRequiredDocRepository;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocEmbed;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.util.BulkResponse;
import rw.ac.ilpd.sharedlibrary.dto.util.ErrorDetail;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntakeApplicationRequiredDocService {

    private final IntakeApplicationRequiredDocRepository repository;
    private final IntakeService intakeService;
    private final IntakeApplicationRequiredDocNameService docNameService;
    private final IntakeApplicationRequiredDocMapper mapper;

    public PagedResponse<IntakeApplicationRequiredDocResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
        );

        Page<IntakeApplicationRequiredDoc> pageResult = repository.findAll(pageable);

        return new PagedResponse<>(
                pageResult.getContent().stream().map(mapper::fromIntakeApplicationRequiredDoc).toList(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.isLast()
        );
    }

    public IntakeApplicationRequiredDocResponse get(UUID id) {
        IntakeApplicationRequiredDoc entity = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Required Doc not found"));
        return mapper.fromIntakeApplicationRequiredDoc(entity);
    }

    public BulkResponse<IntakeApplicationRequiredDocResponse> create(IntakeApplicationRequiredDocRequest request)
    {
        log.info("Creating Intake Application Required Docs");
        BulkResponse<IntakeApplicationRequiredDocResponse>
                bulkResponse = new BulkResponse<>();

        // validate that there are no duplicates in the list of requests
        // map to group requests together and find duplicate requests
        Map<String, List<IntakeApplicationRequiredDocEmbed>> groupedRequests = new HashMap<>();

        // iterate over requests to find duplicate
        // duplicate request is when 2 requests have the same intakeIds with the same requiredDocNameIds too
        for (IntakeApplicationRequiredDocEmbed docName : request.getRequiredDocument())
        {
            String key = docName.getRequiredDocNameId();
            groupedRequests.computeIfAbsent(key, k -> new ArrayList<>()).add(docName);
        }

        // iterate over the hash map to find duplicate requests
        for (Map.Entry<String, List<IntakeApplicationRequiredDocEmbed>> entry : groupedRequests.entrySet())
        {
            if (entry.getValue().size() > 1)
            {
                log.debug("Found duplicate Intake Application Required Docs: {} in request"
                        , entry.getValue().size());
                // add duplicates to the list of failed requests
                bulkResponse.setFailed(entry.getValue().stream().map(
                        value -> new ErrorDetail<>(
                                "Duplicate request",
                                mapper.fromIntakeApplicationRequiredDocRequest(request, value)
                        )).toList());

                // return an error response
                throw new BulkResponseException("Duplicate Intake Application Required Docs ",
                        bulkResponse);
            }
        }

        // Validate intakes in the requests exist in the database
        Intake intake = intakeService.getEntity(UUID.fromString(request.getIntakeId()))
                            .orElseThrow(() ->
                                    {
                                        log.debug("Intake id in Application Required Doc Not Found: {}"
                                                , request.getIntakeId());
                                        return new EntityNotFoundException("Intake " + request.getIntakeId() + " not found");
                                    });

        for (int i = 0; i < request.getRequiredDocument().size(); i++)
        {
            // Validate that the docName in the requests already exists in the DB
            if (!docNameService.requiredDocNameExists(UUID
                    .fromString(request.getRequiredDocument().get(i).getRequiredDocNameId())))
            {
                log.debug("Required doc name in Application Required Doc Not Found: {}",
                        request.getRequiredDocument().get(i).getRequiredDocNameId());
                // add requests with no intake to the failed requests
                bulkResponse.addFailed(new ErrorDetail<>("Doc name provided does not exist",
                        mapper.fromIntakeApplicationRequiredDocRequest(request,
                                request.getRequiredDocument().get(i))));

                // throw exception for missing docName id
                throw new BulkResponseException("Required docName ID not found",
                        bulkResponse);
            }

            // validate if the request already exists in the database
            if (repository.existsByIntakeAndDocumentRequiredName(
                    intakeService.getEntity(UUID.fromString(request.getIntakeId())).get(),
                    docNameService.getEntity(UUID.fromString(
                            request.getRequiredDocument().get(i).getRequiredDocNameId())).get()
            ))
            {
                log.debug("Intake id and Application Required DocName already exists in DB");
                // add requests with no intake to the failed requests
                bulkResponse.addFailed(new ErrorDetail<>("Document Intake pair provided already exists",
                        mapper.fromIntakeApplicationRequiredDocRequest(request,
                                request.getRequiredDocument().get(i))));

                // remove failed request from requests list
                throw new BulkResponseException("Intake ID and Required DocName pair already exists in DB",
                        bulkResponse);
            }
        }

        // list to hold documents to save
        List<IntakeApplicationRequiredDoc> docsToSave = new ArrayList<>();

        for (int i = 0; i < request.getRequiredDocument().size(); i++)
        {
            docsToSave.add(mapper.toIntakeApplicationRequiredDoc(intake,
                    docNameService.getEntity(UUID.fromString(
                            request.getRequiredDocument().get(i).getRequiredDocNameId())).get(),
                    request.getRequiredDocument().get(i).isRequired()));
        }

        // save the documents at once
        List<IntakeApplicationRequiredDoc> savedDocs = repository.saveAll(docsToSave);
        log.info("Created Intake Application Required Docs successfully");

        for (IntakeApplicationRequiredDoc savedDoc : savedDocs)
        {
            bulkResponse.addSuccessful(mapper.fromIntakeApplicationRequiredDoc(savedDoc));
        }

        return bulkResponse;
    }

    public BulkResponse<IntakeApplicationRequiredDocResponse> edit(UUID id, IntakeApplicationRequiredDocRequest request)
    {
        IntakeApplicationRequiredDoc existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Required Doc not found"));

        BulkResponse<IntakeApplicationRequiredDocResponse>
                bulkResponse = new BulkResponse<>();

        Intake intake = intakeService.getEntity(UUID.fromString(request.getIntakeId()))
                .orElseThrow(() -> new EntityNotFoundException("No intake found with this id"));

        for (int i = 0; i < request.getRequiredDocument().size(); i++)
        {
            // Validate that the docName in the requests already exists in the DB
            if (!docNameService.requiredDocNameExists(UUID
                    .fromString(request.getRequiredDocument().get(i).getRequiredDocNameId())))
            {
                log.debug("Required doc name in Application Required Doc Not Found: {}",
                        request.getRequiredDocument().get(i).getRequiredDocNameId());
                // add requests with no intake to the failed requests
                bulkResponse.addFailed(new ErrorDetail<>("Doc name provided does not exist",
                        mapper.fromIntakeApplicationRequiredDocRequest(request,
                                request.getRequiredDocument().get(i))));

                // throw exception for missing docName id
                throw new BulkResponseException("Required doc name not found: ", bulkResponse);
            }

            // validate if the request already exists in the database
            if (repository.existsByIntakeAndDocumentRequiredName(
                    intakeService.getEntity(UUID.fromString(request.getIntakeId())).get(),
                    docNameService.getEntity(UUID.fromString(
                            request.getRequiredDocument().get(i).getRequiredDocNameId())).get()
            ))
            {
                log.debug("Intake id and Application Required DocName already exists in DB");
                // add requests with no intake to the failed requests
                bulkResponse.addFailed(new ErrorDetail<>("Document Intake pair provided already exists",
                        mapper.fromIntakeApplicationRequiredDocRequest(request,
                                request.getRequiredDocument().get(i))));

                // remove failed request from requests list
                throw new BulkResponseException("Intake Required DocName pair already exists",
                        bulkResponse);
            }
        }

        // list to hold documents to save
        List<IntakeApplicationRequiredDoc> docsToUpdate = new ArrayList<>();

        for (int i = 0; i < request.getRequiredDocument().size(); i++)
        {
            docsToUpdate.add(mapper.toIntakeApplicationRequiredDoc(intake,
                    docNameService.getEntity(UUID.fromString(
                            request.getRequiredDocument().get(i).getRequiredDocNameId())).get(),
                    request.getRequiredDocument().get(i).isRequired()));
        }

        List<IntakeApplicationRequiredDoc> updatedDocs = repository.saveAll(docsToUpdate);
        log.info("Created Intake Application Required Docs successfully");

        for (IntakeApplicationRequiredDoc updatedDoc : updatedDocs)
        {
            bulkResponse.addSuccessful(mapper.fromIntakeApplicationRequiredDoc(updatedDoc));
        }

        return bulkResponse;
    }

    public IntakeApplicationRequiredDocResponse patch(UUID id, Map<String, Object> updates) {
        IntakeApplicationRequiredDoc existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Required Doc not found"));

        // Patch intake
        if (updates.containsKey("intakeId")) {
            Object value = updates.get("intakeId");
            if (value instanceof String intakeIdStr) {
                UUID intakeId = UUID.fromString(intakeIdStr);
                Intake intakeEntity = intakeService.getEntity(intakeId).orElseThrow(() -> new EntityNotFoundException("No intake found with this id"));
                existing.setIntake(intakeEntity);
            }
        }

        // Patch requiredDocName
        if (updates.containsKey("requiredDocNameId")) {
            Object value = updates.get("requiredDocNameId");
            if (value instanceof String docIdStr) {
                UUID docId = UUID.fromString(docIdStr);
                IntakeApplicationRequiredDocName docEntity = docNameService.getEntity(docId)
                        .orElseThrow(() -> new EntityNotFoundException("Required Doc not found"));
                existing.setDocumentRequiredName(docEntity);
            }
        }

        // Patch isRequired
        if (updates.containsKey("isRequired")) {
            Object value = updates.get("isRequired");
            if (value instanceof Boolean requiredFlag) {
                existing.setIsRequired(requiredFlag);
            }
        }

        return mapper.fromIntakeApplicationRequiredDoc(repository.save(existing));
    }

    public Boolean delete(UUID id) {
        IntakeApplicationRequiredDoc entity = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Required Doc not found"));
        repository.delete(entity);
        return true;
    }

    public Optional<IntakeApplicationRequiredDoc> getEntity(UUID id) {
        return repository.findById(id);
    }

    public List<IntakeApplicationRequiredDoc> getDocsByIntake(UUID intakeId)
    {
        return repository.findByIntake(intakeService.getEntity(intakeId).get());
    }
}
