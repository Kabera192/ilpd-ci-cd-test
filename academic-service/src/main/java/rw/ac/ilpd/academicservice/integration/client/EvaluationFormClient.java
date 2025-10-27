package rw.ac.ilpd.academicservice.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormResponse;

@FeignClient(name = "REPORT-SERVICE",path = "/evaluation-forms")
public interface EvaluationFormClient {
    @GetMapping("/{id}")
    EvaluationFormResponse findEvaluationFormById(@PathVariable String id);
}
