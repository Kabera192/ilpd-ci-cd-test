/*
 * File: EvaluationFormQuestionSectionService.java
 *
 * Description: Service class for managing Evaluation Form Question Sections.
 *              Handles business logic for CRUD operations, including validation and relationship enforcement.
 *
 * Last Modified Date: 2025-08-13
 */
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
import rw.ac.ilpd.reportingservice.mapper.EvaluationFormQuestionSectionMapper;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormQuestionSection;
import rw.ac.ilpd.reportingservice.repository.nosql.EvaluationFormQuestionRepository;
import rw.ac.ilpd.reportingservice.repository.nosql.EvaluationFormQuestionSectionRepository;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionSectionRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionSectionResponse;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EvaluationFormQuestionSectionService
{
    private final EvaluationFormQuestionSectionRepository repository;
    private final EvaluationFormQuestionRepository questionRepository;
    private final EvaluationFormQuestionSectionMapper evaluationFormQuestionSectionMapper;

    /*
    * Function to handle the creation of form sections
    * */
    public EvaluationFormQuestionSectionResponse create(EvaluationFormQuestionSectionRequest request)
    {
        log.info("Creating question section: {}", request);
        EvaluationFormQuestionSection entity = evaluationFormQuestionSectionMapper
                .toEvaluationFormQuestionSection(request);

        if (repository.existsByTitle(request.getTitle()))
        {
            log.error("Section with the title: {} already exists", request.getTitle());
            throw new EntityAlreadyExists("Section with the title " + request.getTitle() + " already exists");
        }

        entity = repository.save(entity);
        return evaluationFormQuestionSectionMapper.fromEvaluationFormQuestionSection(entity);
    }

    /*
    * Function that fetches all existing form sections
    * */
    @Transactional(readOnly = true)
    public Page<EvaluationFormQuestionSectionResponse> getAll(Pageable pageable)
    {
        log.info("Fetching all question sections with pageable: {}", pageable);
        return repository.findAll(pageable)
                .map(evaluationFormQuestionSectionMapper::fromEvaluationFormQuestionSection);
    }

    /*
     * Function that fetches an existing form section by id
     * */
    @Transactional(readOnly = true)
    public EvaluationFormQuestionSectionResponse getById(String id)
    {
        log.info("Fetching question section by ID: {}", id);
        return repository.findById(id)
                .map(evaluationFormQuestionSectionMapper::fromEvaluationFormQuestionSection)
                .orElseThrow(() -> new EntityNotFoundException("Question section not found"));
    }

    /*
     * Function that updates a particular existing form section
     * */
    public EvaluationFormQuestionSectionResponse update(String id, EvaluationFormQuestionSectionRequest request)
    {
        log.info("Updating question section ID: {} with {}", id, request);
        EvaluationFormQuestionSection entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question section not found"));
        entity.setTitle(request.getTitle());
        entity = repository.save(entity);
        return evaluationFormQuestionSectionMapper.fromEvaluationFormQuestionSection(entity);
    }

    /*
     * Function that deletes a particular existing form section
     * */
    public void delete(String id)
    {
        log.info("Deleting question section ID: {}", id);

        if (!repository.existsById(id))
        {
            log.error("Question section not found with ID: {}", id);
            throw new EntityNotFoundException("Question section requested not found");
        }

        if (!questionRepository.findBySectionId(id).isEmpty())
        {
            log.error("Cannot delete section: {} with associated questions", id);
            throw new BadRequestException("Cannot delete question section with associated questions");
        }
        repository.deleteById(id);
    }
}