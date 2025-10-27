package rw.ac.ilpd.reportingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.reportingservice.exception.BadRequestException;
import rw.ac.ilpd.reportingservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.reportingservice.exception.EntityNotFoundException;
import rw.ac.ilpd.reportingservice.mapper.EvaluationFormQuestionGroupMapper;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormQuestionGroup;
import rw.ac.ilpd.reportingservice.repository.nosql.EvaluationFormQuestionGroupRepository;
import rw.ac.ilpd.reportingservice.repository.nosql.EvaluationFormQuestionRepository;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionGroupResponse;

/*
 * File: EvaluationFormQuestionGroupService.java
 *
 * Description: Service class for managing Evaluation Form Question Groups.
 *              Handles business logic for CRUD operations, including validation and relationship enforcement.
 *
 * Last Modified Date: 2025-08-13
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EvaluationFormQuestionGroupService
{
    private final EvaluationFormQuestionGroupRepository repository;
    private final EvaluationFormQuestionRepository questionRepository;
    private final EvaluationFormQuestionGroupMapper evaluationFormQuestionGroupMapper;

    /*
    * Function that creates a form question group
    *
    * returns form question group object that has been created.
    * */
    public EvaluationFormQuestionGroupResponse create(EvaluationFormQuestionGroupRequest request)
    {
        log.info("Creating question group: {}", request);
        EvaluationFormQuestionGroup entity = evaluationFormQuestionGroupMapper
                .toEvaluationFormQuestionGroup(request);

        if (repository.existsByName(request.getName()))
        {
            log.error("Question group with name {} already exists", request.getName());
            throw new EntityAlreadyExists("Question group with name " + request.getName() + " already exists");
        }

        entity = repository.save(entity);
        return evaluationFormQuestionGroupMapper.fromEvaluationFormQuestionGroup(entity);
    }

    /*
     * Function that fetches a list of form question groups
     *
     * returns a page of form question group.
     * */
    @Transactional(readOnly = true)
    public Page<EvaluationFormQuestionGroupResponse> getAll(Pageable pageable)
    {
        log.info("Fetching all question groups with pageable: {}", pageable);
        return repository.findAll(pageable)
                .map(evaluationFormQuestionGroupMapper::fromEvaluationFormQuestionGroup);
    }

    /*
     * Function that fetches form question group by id
     *
     * returns form question group object
     * */
    @Transactional(readOnly = true)
    public EvaluationFormQuestionGroupResponse getById(String id)
    {
        log.info("Fetching question group by ID: {}", id);
        return repository.findById(id)
                .map(evaluationFormQuestionGroupMapper::fromEvaluationFormQuestionGroup)
                .orElseThrow(() -> new EntityNotFoundException("Question group requested not found"));
    }

    /*
     * Function that updates a form question group
     *
     * returns form question group object that has been updated.
     * */
    public EvaluationFormQuestionGroupResponse update(String id, EvaluationFormQuestionGroupRequest request)
    {
        log.info("Updating question group ID: {} with {}", id, request);
        EvaluationFormQuestionGroup entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question group requested not found"));

        if (repository.existsByName(request.getName()))
        {
            log.error("Question group with name {} already exists", request.getName());
            throw new EntityAlreadyExists("Question group with name " + request.getName() + " already exists");
        }

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity = repository.save(entity);
        return evaluationFormQuestionGroupMapper.fromEvaluationFormQuestionGroup(entity);
    }

    /*
     * Function that deletes a form question group
     *
     * does not return anything.
     * */
    @Transactional
    public void delete(String id)
    {
        log.info("Deleting question group ID: {}", id);

        if (!repository.existsById(id))
        {
            log.error("Question group: {} requested not found", id);
            throw new EntityNotFoundException("Question group requested not found");
        }

        // check if group does not have questions associated with it
        if (questionRepository.findByGroupId(id) != null)
        {
            log.error("Question group with id {} has questions associated with it", id);
            throw new BadRequestException("Cannot delete question group with associated questions");
        }
        repository.deleteById(id);
    }
}