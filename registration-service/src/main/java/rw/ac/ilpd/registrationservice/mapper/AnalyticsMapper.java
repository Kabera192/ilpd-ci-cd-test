package rw.ac.ilpd.registrationservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationAnalyticsResponse;

/**
 * A mapper component for transforming application analytics data into a structured response.
 * <p>
 * This class provides a method to convert various statistical metrics regarding applications
 * and their associated documents into an instance of {@link ApplicationAnalyticsResponse}.
 */
@Component
public class AnalyticsMapper {

    public ApplicationAnalyticsResponse toAnalyticsResponse(long totalApps, long pendingApps, long approvedApps,
                                                            long rejectedApps, long deferredApps, long totalDocs,
                                                            long pendingDocs, long approvedDocs, long rejectedDocs,
                                                            double avgDocsPerApp, double avgProcessingTime) {

        return ApplicationAnalyticsResponse.builder().totalApplications(totalApps).pendingApplications(pendingApps)
                .approvedApplications(approvedApps).rejectedApplications(rejectedApps)
                .deferredApplications(deferredApps).totalDocuments(totalDocs).pendingDocuments(pendingDocs)
                .approvedDocuments(approvedDocs).rejectedDocuments(rejectedDocs)
                .averageDocumentsPerApplication(avgDocsPerApp).averageProcessingTimeHours(avgProcessingTime).build();
    }
}
