package rw.ac.ilpd.sharedlibrary.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultipleAcademicBackgroundResponse {

    private List<AcademicBackgroundResponse> academicBackgrounds;

    private int totalCount;

    private String userId;
}