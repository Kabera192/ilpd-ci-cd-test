package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.integration.client.EvaluationFormClient;
import rw.ac.ilpd.academicservice.mapper.ShortCourseTopicLecturerEvaluationMapper;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopicLecturerEvaluation;
import rw.ac.ilpd.academicservice.repository.sql.ShortCourseTopicLecturerEvaluationRepository;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturerevaluation.ShortCourseTopicLecturerEvaluationRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturerevaluation.ShortCourseTopicLecturerEvaluationResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for managing ShortCourseTopicLecturerEvaluation CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class ShortCourseTopicLecturerEvaluationService {
    private final ShortCourseTopicLecturerEvaluationRepository sctEvaluationRepository;
    private final ShortCourseTopicLecturerEvaluationMapper sctEvaluationMapper;
private final EvaluationFormClient evaluationFormClient;
    /**
     * Creates a new ShortCourseTopicLecturerEvaluation.
     */
    public ResponseEntity<ShortCourseTopicLecturerEvaluationResponse> createEvaluation(
            ShortCourseTopicLecturerEvaluationRequest request
    ) {
sctEvaluationRepository
        .findByShortCourseTopicLecturerId((UUID.fromString(request.getShortCourseTopicLecturerId())))
        .orElseThrow(()->new EntityNotFoundException("ShortCourseTopicLecturerEvaluation not found"));
        EvaluationFormResponse efc=evaluationFormClient.findEvaluationFormById(request.getPriorEvaluationFormId());
        if(efc==null){
            throw new EntityNotFoundException("Evaluation Form not found");
        }
        ShortCourseTopicLecturerEvaluation evaluation = sctEvaluationMapper.toEvaluation(request);
        ShortCourseTopicLecturerEvaluation saved = sctEvaluationRepository.save(evaluation);
        return new ResponseEntity<>(sctEvaluationMapper.fromEvaluation(saved), HttpStatus.CREATED);
    }

    /**
     * Retrieves an evaluation by ID.
     */
    public Optional<ShortCourseTopicLecturerEvaluation> getEntity(String id) {
        return sctEvaluationRepository.findById(UUID.fromString(id));
    }

    public ResponseEntity<ShortCourseTopicLecturerEvaluationResponse> getScTopicLecturerEvaluation(String id) {
        ShortCourseTopicLecturerEvaluation evaluation = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Evaluation not found"));
        return ResponseEntity.ok(sctEvaluationMapper.fromEvaluation(evaluation));
    }
//
//    /**
//     * Retrieves all evaluations with optional filtering for archived (deleted) and search.
//     */
//    public ResponseEntity<List<ShortCourseTopicLecturerEvaluationResponse>> getAllEvaluations(
//            String display,
//            String search
//    ) {
//        List<ShortCourseTopicLecturerEvaluation> evaluations;
//
//        if ("archive".equalsIgnoreCase(display)) {
//            evaluations = search == null || search.isBlank()
//                    ? sctEvaluationRepository.findByIsDeleted(true)
//                    : sctEvaluationRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeleted(search, search, true);
//        } else if ("all".equalsIgnoreCase(display)) {
//            evaluations = search == null || search.isBlank()
//                    ? sctEvaluationRepository.findAll()
//                    : sctEvaluationRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search);
//        } else {
//            evaluations = search == null || search.isBlank()
//                    ? sctEvaluationRepository.findByIsDeleted(false)
//                    : sctEvaluationRepository.findByIsDeletedAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(false, search, search);
//        }
//
//        List<ShortCourseTopicLecturerEvaluationResponse> response = evaluations.stream()
//                .map(sctEvaluationMapper::fromEvaluation)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(response);
//    }
//
//    /**
//     * Retrieves paged evaluations with filtering, search, sorting, and order by.
//     */
//    public ResponseEntity<PagedResponse<ShortCourseTopicLecturerEvaluationResponse>> getPagedEvaluations(
//            int page,
//            int size,
//            String display,
//            String search,
//            String sort,
//            String orderBy
//    ) {
//        Sort.Direction direction = orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
//        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
//
//        Page<ShortCourseTopicLecturerEvaluation> evaluationPage;
//
//        if ("archive".equalsIgnoreCase(display)) {
//            evaluationPage = search == null || search.isBlank()
//                    ? sctEvaluationRepository.findByIsDeleted(true, pageable)
//                    : sctEvaluationRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeleted(
//                        search, search, true, pageable);
//        } else if ("all".equalsIgnoreCase(display)) {
//            evaluationPage = search == null || search.isBlank()
//                    ? sctEvaluationRepository.findAll(pageable)
//                    : sctEvaluationRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
//                        search, search, pageable);
//        } else {
//            evaluationPage = search == null || search.isBlank()
//                    ? sctEvaluationRepository.findByIsDeleted(false, pageable)
//                    : sctEvaluationRepository.findByIsDeletedAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
//                        false, search, search, pageable);
//        }
//
//        List<ShortCourseTopicLecturerEvaluationResponse> content = evaluationPage.getContent().stream()
//                .map(sctEvaluationMapper::fromEvaluation)
//                .collect(Collectors.toList());
//
//        PagedResponse<ShortCourseTopicLecturerEvaluationResponse> response = new PagedResponse<>(
//                content,
//                evaluationPage.getNumber(),
//                evaluationPage.getSize(),
//                evaluationPage.getTotalElements(),
//                evaluationPage.getTotalPages(),
//                evaluationPage.isLast()
//        );
//
//        return ResponseEntity.ok(response);
//    }
//
//    /**
//     * Updates an evaluation by ID.
//     */
//    public ResponseEntity<ShortCourseTopicLecturerEvaluationResponse> updateEvaluation(String id, ShortCourseTopicLecturerEvaluationRequest request) {
//        ShortCourseTopicLecturerEvaluation evaluation = sctEvaluationRepository.findById(UUID.fromString(id))
//                .orElseThrow(() -> new EntityNotFoundException("Evaluation not found"));
//        if (evaluation.isDeleted()) throw new EntityNotFoundException("Evaluation not found");
//
//        ShortCourseTopicLecturerEvaluation updated = sctEvaluationMapper.toEvaluationUpdate(evaluation, request);
//        ShortCourseTopicLecturerEvaluation saved = sctEvaluationRepository.save(updated);
//        return ResponseEntity.ok(sctEvaluationMapper.fromEvaluation(saved));
//    }
//
//    /**
//     * Deletes (archives) an evaluation by ID.
//     */
//    public ResponseEntity<String> deleteEvaluation(String id) {
//        ShortCourseTopicLecturerEvaluation evaluation = sctEvaluationRepository.findById(UUID.fromString(id))
//                .orElseThrow(() -> new EntityNotFoundException("Evaluation not found"));
//        evaluation.setDeleted(true);
//        sctEvaluationRepository.save(evaluation);
//        return ResponseEntity.ok("Evaluation archived successfully.");
//    }
//
//    /**
//     * Restores an archived evaluation by ID.
//     */
//    public ResponseEntity<String> undoDeleteEvaluation(String id) {
//        ShortCourseTopicLecturerEvaluation evaluation = sctEvaluationRepository.findById(UUID.fromString(id))
//                .orElseThrow(() -> new EntityNotFoundException("Evaluation not found"));
//        if (!evaluation.isDeleted()) {
//            return ResponseEntity.status(404).body("Evaluation is not archived.");
//        }
//        evaluation.setDeleted(false);
//        sctEvaluationRepository.save(evaluation);
//        return ResponseEntity.ok("Evaluation restored successfully.");
//    }
}
