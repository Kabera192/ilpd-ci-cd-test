package rw.ac.ilpd.reportingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.reportingservice.mapper.EvaluationFormMapper;
import rw.ac.ilpd.reportingservice.mapper.EvaluationFormUserFillerMapper;
import rw.ac.ilpd.reportingservice.mapper.EvaluationFormAnswerMapper;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationForm;
import rw.ac.ilpd.reportingservice.model.nosql.embedding.EvaluationFormUserFiller;
import rw.ac.ilpd.reportingservice.model.nosql.embedding.EvaluationFormAnswer;
import rw.ac.ilpd.reportingservice.repository.nosql.EvaluationFormRepository;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.*;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EvaluationFormService {

    private final EvaluationFormRepository repository;
    private final EvaluationFormMapper formMapper;
    private final EvaluationFormUserFillerMapper fillerMapper;
    private final EvaluationFormAnswerMapper answerMapper;

    /** Create a new evaluation form */
    public EvaluationFormResponse createForm(EvaluationFormRequest request) {
        EvaluationForm entity = formMapper.fromEvaluationFormRequest(request);
        EvaluationForm saved = repository.save(entity);
        return formMapper.toEvaluationFormResponse(saved);
    }

    /** Get one evaluation form by ID */
    public Optional<EvaluationFormResponse> getForm(String id) {
        return repository.findById(id)
                .map(formMapper::toEvaluationFormResponse);
    }

    /** Get all evaluation forms */
    public PagedResponse<EvaluationFormResponse> getAllForms(int page, int size, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<EvaluationForm> formPage = repository.findAll(pageable);

        List<EvaluationFormResponse> content = formPage.getContent()
                .stream()
                .map(formMapper::toEvaluationFormResponse)
                .toList();

        return new PagedResponse<>(
                content,
                formPage.getNumber(),
                formPage.getSize(),
                formPage.getTotalElements(),
                formPage.getTotalPages(),
                formPage.isLast()
        );
    }


    /** Delete one evaluation form by ID */
    public boolean deleteForm(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /** Add a user filler to a form */
    public Optional<EvaluationFormUserFillerResponse> addUserFiller(String formId, EvaluationFormUserFillerRequest request) {
        return repository.findById(formId).map(form -> {
            EvaluationFormUserFiller filler = fillerMapper.fromEvaluationFormUserFillerRequest(request);
            form.getUserFillers().add(filler);
            repository.save(form);
            return fillerMapper.toEvaluationFormUserFillerResponse(filler);
        });
    }

    /** Update a user filler inside a form */
    public Optional<EvaluationFormUserFillerResponse> updateUserFiller(String formId, String fillerId, EvaluationFormUserFillerRequest request) {
        return repository.findById(formId).map(form -> {
            form.getUserFillers().stream()
                    .filter(f -> f.getId().equals(fillerId))
                    .findFirst()
                    .ifPresent(f -> {
                        f.setLevel(request.getLevel());
                        f.setLevelId(request.getLevelId());
                        f.setRoleId(request.getRoleId());
                        f.setIntakeId(request.getIntakeId());
                    });
            repository.save(form);
            return form.getUserFillers().stream()
                    .filter(f -> f.getId().equals(fillerId))
                    .findFirst()
                    .map(fillerMapper::toEvaluationFormUserFillerResponse)
                    .orElse(null);
        });
    }

    /** Delete a user filler inside a form */
    public boolean deleteUserFiller(String formId, String fillerId) {
        return repository.findById(formId).map(form -> {
            boolean removed = form.getUserFillers().removeIf(f -> f.getId().equals(fillerId));
            if (removed) repository.save(form);
            return removed;
        }).orElse(false);
    }

    /** List all user fillers for a form */
    public List<EvaluationFormUserFillerResponse> listUserFillers(String formId) {
        EvaluationForm form = repository.findById(formId)
                .orElseThrow(() -> new IllegalArgumentException("Evaluation form not found"));
        return form.getUserFillers().stream()
                .map(fillerMapper::toEvaluationFormUserFillerResponse)
                .collect(Collectors.toList());
    }

    /** Add an answer to a form */
    public Optional<EvaluationFormAnswerResponse> addAnswer(String formId, EvaluationFormAnswerRequest request) {
        return repository.findById(formId).map(form -> {
            EvaluationFormAnswer answer = answerMapper.fromEvaluationFormAnswerRequest(request);
            form.getAnswers().add(answer);
            repository.save(form);
            return answerMapper.toEvaluationFormAnswerResponse(answer);
        });
    }

    /** Update an answer inside a form */
    public Optional<EvaluationFormAnswerResponse> updateAnswer(String formId, String answerId, EvaluationFormAnswerRequest request) {
        return repository.findById(formId).map(form -> {
            form.getAnswers().stream()
                    .filter(a -> a.getId().equals(answerId))
                    .findFirst()
                    .ifPresent(a -> {
                        a.setQuestionId(request.getQuestionId());
                        a.setSelectedOptionId(request.getSelectedOptionId());
                        a.setWrittenAnswer(request.getWrittenAnswer());
                        a.setEmail(request.getEmail());
                    });
            repository.save(form);
            return form.getAnswers().stream()
                    .filter(a -> a.getId().equals(answerId))
                    .findFirst()
                    .map(answerMapper::toEvaluationFormAnswerResponse)
                    .orElse(null);
        });
    }

    /** Delete an answer inside a form */
    public boolean deleteAnswer(String formId, String answerId) {
        return repository.findById(formId).map(form -> {
            boolean removed = form.getAnswers().removeIf(a -> a.getId().equals(answerId));
            if (removed) repository.save(form);
            return removed;
        }).orElse(false);
    }

    /** List all answers for a form */
    public List<EvaluationFormAnswerResponse> listAnswers(String formId) {
        EvaluationForm form = repository.findById(formId)
                .orElseThrow(() -> new IllegalArgumentException("Evaluation form not found"));
        return form.getAnswers().stream()
                .map(answerMapper::toEvaluationFormAnswerResponse)
                .collect(Collectors.toList());
    }
}
