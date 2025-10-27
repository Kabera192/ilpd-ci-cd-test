package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rw.ac.ilpd.academicservice.model.nosql.document.StudentAssessmentSubmission;
import rw.ac.ilpd.sharedlibrary.dto.assessment.StudentAssessmentSubmissionRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessment.StudentAssessmentSubmissionResponse;

@Mapper(componentModel = "spring")
public interface StudentAssessmentSubmissionMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "correctedBy", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "documents", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "submittedBy", ignore = true)
    StudentAssessmentSubmission toStudentAssessmentSubmission(
            StudentAssessmentSubmissionRequest studentAssessmentSubmissionRequest);

    StudentAssessmentSubmissionResponse fromStudentAssessmentSubmission(
            StudentAssessmentSubmission studentAssessmentSubmission);
}
