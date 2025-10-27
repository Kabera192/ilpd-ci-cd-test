package rw.ac.ilpd.mis.shared.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationAnalyticsResponse {
    private long totalApplications;
    private long pendingApplications;
    private long approvedApplications;
    private long rejectedApplications;
    private long deferredApplications;

    private long totalDocuments;
    private long pendingDocuments;
    private long approvedDocuments;
    private long rejectedDocuments;

    private double averageDocumentsPerApplication;
    private double averageProcessingTimeHours;
}
