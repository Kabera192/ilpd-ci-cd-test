package rw.ac.ilpd.mis.shared.dto.prohibitedtransfercouple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.mis.shared.dto.studymodesession.StudyModeSessionResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProhibitedTransferCoupleStudySessionDetailResponse {
    private String id;
    private StudyModeSessionResponse session1Id;
    private StudyModeSessionResponse session2Id;

}
