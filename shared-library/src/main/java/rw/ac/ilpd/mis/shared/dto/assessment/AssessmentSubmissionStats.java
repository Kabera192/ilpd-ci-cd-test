package rw.ac.ilpd.mis.shared.dto.assessment;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AssessmentSubmissionStats
{
    private long totalSubmissions;
    private long gradedSubmissions;
    private long pendingSubmissions;
    private Double averageGrade;
}
