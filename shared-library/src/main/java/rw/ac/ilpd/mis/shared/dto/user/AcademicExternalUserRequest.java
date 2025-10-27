package rw.ac.ilpd.mis.shared.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.sharedlibrary.dto.validation.address.OptionalValidAddress;
import rw.ac.ilpd.sharedlibrary.dto.validation.email.ValidEmail;
import rw.ac.ilpd.sharedlibrary.dto.validation.matchpattern.MatchesPattern;
import rw.ac.ilpd.sharedlibrary.dto.validation.phonenumber.ValidPhoneNumber;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AcademicExternalUserRequest {
    @NotBlank(message = "Next of keen name can not be blank")
    @Size(min = 2,message = "Too short name to save")
    private String name;
    @ValidEmail(message = "Type a correct email address",required = false)
    private String email;
    @ValidPhoneNumber(message = "Invalid phone number the correct format [country code] [phone number]")
    private String phoneNumber;
    @OptionalValidAddress(message = "Address must be 3–250 characters long and may only contain letters [A-az-Z], numbers[0-9], spaces [ ], commas[,], periods[.], apostrophes['], dashes[..], or slashes[/].")
    private String address;

    @MatchesPattern(regexp = "^(PARENT|FAMILY_MEMBER|PREFER_NOT_TO_SAY|OTHER|FRIEND)$",
            message = "Relation ship is either : PARENT ,FAMILY_MEMBER ,PREFER_NOT_TO_SAY , FRIEND or OTHER",required = false)
    private String relationship;
    private MultipartFile recommendationFile;
//    @MatchesPattern(regexp = "^(HUMAN | ORGANIZATION)$",
//            message = "Sponsor type is either : HUMAN or ORGANIZATION",required = false)
    private String sponsorType;
    @Pattern(regexp = "^(SPONSOR|NEXT_OF_KIN)$",message = "Registered external user is either SPONSOR or NEXT_OF_KIN")
    private String externalUserType;
}