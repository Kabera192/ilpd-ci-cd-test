package rw.ac.ilpd.sharedlibrary.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDocumentSubmissionAnalyticsResponse {
    private long totalSubmissions;
    private long pendingSubmissions;
    private long approvedSubmissions;
    private long rejectedSubmissions;
    private long totalComments;
    private double averageCommentsPerSubmission;
}
