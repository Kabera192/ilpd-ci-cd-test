/*
 * File: EvaluationFormQuestionService.java
 *
 * Description: Service class for managing Evaluation Form Questions.
 *              Handles business logic for CRUD operations, including validation of group and section existence.
 *
 * Last Modified Date: 2025-08-13
 */
package rw.ac.ilpd.reportingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.reportingservice.exception.EntityNotFoundException;
import rw.ac.ilpd.reportingservice.mapper.EvaluationFormOptionMapper;
import rw.ac.ilpd.reportingservice.mapper.EvaluationFormQuestionMapper;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormQuestion;
import rw.ac.ilpd.reportingservice.repository.nosql.EvaluationFormQuestionRepository;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionResponse;
import rw.ac.ilpd.sharedlibrary.enums.QuestionType;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EvaluationFormQuestionService
{
    private final EvaluationFormQuestionRepository repository;
    private final EvaluationFormQuestionGroupService groupService;
    private final EvaluationFormQuestionSectionService sectionService;
    private final EvaluationFormOptionMapper evaluationFormOptionMapper;
    private final EvaluationFormQuestionMapper mapper = Mappers.getMapper(EvaluationFormQuestionMapper.class);

    /*
    * Function that handles the creation of an evaluation form question
    *
    * Returns an EvaluationFormQuestionResponse object.
    * */
    public EvaluationFormQuestionResponse create(EvaluationFormQuestionRequest request)
    {
        log.info("Creating question: {}", request);
        validateFormQuestion(request);

        log.info("Creating evaluation form question: {}", request);
        EvaluationFormQuestion entity = mapper.toEvaluationFormQuestion(request);
        entity = repository.save(entity);
        return mapper.fromEvaluationFormQuestion(entity);
    }

    /*
     * Function that handles the fetching of all evaluation form questions
     *
     * Returns  Page of evaluation form questions.
     * */
    @Transactional(readOnly = true)
    public Page<EvaluationFormQuestionResponse> getAll(Pageable pageable)
    {
        log.info("Fetching all questions with pageable: {}", pageable);
        return repository.findAll(pageable).map(mapper::fromEvaluationFormQuestion);
    }

    /*
     * Function that handles fetching of an evaluation form question by id
     *
     * Returns an EvaluationFormQuestionResponse object.
     * */
    @Transactional(readOnly = true)
    public EvaluationFormQuestionResponse getById(String id)
    {
        log.info("Fetching question by ID: {}", id);
        return repository.findById(id)
                .map(mapper::fromEvaluationFormQuestion)
                .orElseThrow(() -> new EntityNotFoundException("Question requested does not exist"));
    }

    /*
     * Function that handles updating of an evaluation form question
     *
     * Returns an EvaluationFormQuestionResponse object that has been updated.
     * */
    public EvaluationFormQuestionResponse update(String id, EvaluationFormQuestionRequest request)
    {
        log.info("Updating question ID: {} with {}", id, request);
        EvaluationFormQuestion entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question requested does not exist"));

        validateFormQuestion(request);

        entity.setGroupId(request.getGroupId());
        entity.setQuestionText(request.getQuestionText());
        entity.setWeight(request.getWeight());
        entity.setSectionId(request.getSectionId());
        entity.setType(request.getType());
        entity.setOptions(request.getOptions().stream()
                .map(evaluationFormOptionMapper::toEvaluationFormOption)
                .collect(Collectors.toList()));

        entity = repository.save(entity);
        return mapper.fromEvaluationFormQuestion(entity);
    }

    /*
     * Function that handles the hard deletion of an evaluation form question
     *
     * Does not return anything
     * */
    public void delete(String id)
    {
        log.info("Deleting question ID: {}", id);
        repository.deleteById(id);
    }

    /*
     * Utility function that validates whether a question adheres to relationships
     * with the form group and form section documents, and if options have been
     * provided.
     *
     * Does not return anything but throws exceptions in case of issues in validation
     * */
    private void validateFormQuestion(EvaluationFormQuestionRequest request)
    {
        if (groupService.getById(request.getGroupId()) == null)
        {
            log.info("Group does not exist for question: {}", request);
            throw new EntityNotFoundException("Group not found when creating evaluation form question");
        }
        if (sectionService.getById(request.getSectionId()) == null)
        {
            log.info("Section does not exist for question: {}", request);
            throw new EntityNotFoundException("Section not found when creating evaluation form question");
        }
        if ((request.getType().equals(QuestionType.OPEN_ENDED) ||
                request.getType().equals(QuestionType.RANGE)) && !request.getOptions().isEmpty())
        {
            log.info("Options are not supported for questions with type: {}", request.getType());
            throw new IllegalArgumentException("Options are not supported for question type: "
                    + request.getType());
        }
        if (!(request.getType().equals(QuestionType.OPEN_ENDED) ||
                request.getType().equals(QuestionType.RANGE)) && request.getOptions().isEmpty())
        {
            log.info("Options are required for questions with type: {}", request.getType());
            throw new IllegalArgumentException("Options are required for question type: "
                    + request.getType());
        }
    }

    /*
     * Function that returns a list of form question responses that belong
     * to a particular section.
     *
     * Returns a list of form question responses
     * */
    public List<EvaluationFormQuestionResponse> findBySectionId(String id)
    {
        log.info("Finding questions that belong to section with ID: {}", id);
        List<EvaluationFormQuestion> responses = repository.findBySectionId(id);

        if (responses.isEmpty())
        {
            log.error("No questions associated with section: {}", id);
            return null;
        }

        return responses.stream()
                .map(mapper::fromEvaluationFormQuestion)
                .collect(Collectors.toList());
    }

    /*
     * Function that returns a list of form question responses that belong
     * to a particular question group.
     *
     * Returns a list of form question responses
     * */
    public List<EvaluationFormQuestionResponse> findByGroupId(String id)
    {
        log.info("Finding questions that belong to group with ID: {}", id);
        List<EvaluationFormQuestion> responses = repository.findByGroupId(id);

        if (responses.isEmpty())
        {
            log.error("No questions found for group with id: {}", id);
            return null;
        }

        return responses.stream()
                .map(mapper::fromEvaluationFormQuestion)
                .collect(Collectors.toList());
    }
}