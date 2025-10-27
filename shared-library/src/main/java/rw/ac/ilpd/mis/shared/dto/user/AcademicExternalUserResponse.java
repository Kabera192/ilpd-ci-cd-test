package rw.ac.ilpd.mis.shared.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AcademicExternalUserResponse {
    private String id;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String relationship;

    private String recommendationFileId;

    private String sponsorType;

    private String externalUserType;

    private String createdBy;
}
