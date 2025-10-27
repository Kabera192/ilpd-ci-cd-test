package rw.ac.ilpd.reportingservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingRequest;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingResponse;

import java.util.List;

public interface EvaluationFormTypeService {

    EvaluationFormTypeResponse create(EvaluationFormTypeRequest request);

    EvaluationFormTypeResponse getById(String id);

    EvaluationFormTypeResponse update(String id, EvaluationFormTypeRequest request);

    void delete(String id);

    void softDelete(String id);

    Page<EvaluationFormTypeResponse> getAll(Pageable pageable);

    Page<EvaluationFormTypeResponse> getAllActive(Pageable pageable);

    Page<EvaluationFormTypeResponse> searchByName(String name, Pageable pageable);

    List<EvaluationFormTypeResponse> getAllActiveList();

    boolean existsById(String id);

    boolean existsByName(String name);

    long countActive();

}