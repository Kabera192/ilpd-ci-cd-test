package rw.ac.ilpd.sharedlibrary.dto.prohibitedtransfercouple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.studymodesession.StudyModeSessionResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProhibitedTransferCoupleStudySessionDetailResponse {
    private String id;
    private StudyModeSessionResponse session1Id;
    private StudyModeSessionResponse session2Id;

}
