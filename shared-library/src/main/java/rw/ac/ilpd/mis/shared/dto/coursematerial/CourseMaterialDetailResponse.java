package rw.ac.ilpd.mis.shared.dto.coursematerial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseMaterialDetailResponse {
    private String id;
    private String title;
    private String description;
    private String url;
    private String createdAt;
}
