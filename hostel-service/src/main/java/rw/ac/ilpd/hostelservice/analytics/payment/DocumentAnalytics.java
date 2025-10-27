package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Schema(description = "Document management analytics")
public class DocumentAnalytics {
//    @Schema(description = "Total documents uploaded", example = "2850")
    private Long totalDocuments;

//    @Schema(description = "Verified documents count", example = "2150")
    private Long verifiedDocuments;

//    @Schema(description = "Pending verification count", example = "450")
    private Long pendingVerification;

//    @Schema(description = "Rejected documents count", example = "185")
    private Long rejectedDocuments;

//    @Schema(description = "Documents deleted count", example = "65")
    private Long deletedDocuments;

//    @Schema(description = "Document verification rate", example = "75.4")
    private Double verificationRate;

//    @Schema(description = "Average documents per payment", example = "2.28")
    private Double averageDocumentsPerPayment;

//    @Schema(description = "Document status distribution")
    private Map<String, Long> statusDistribution;
}