/**
 * Request payload for defining a prohibited transfer couple in the ILPD system.
 *
 * <p>This DTO contains two session IDs.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.prohibitedtransfercouple;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
    public class ProhibitedTransferCoupleRequest {

        @NotBlank(message = "First session is required")
        @ValidUuid(message = "Invalid Study mode session  format")
        private String fromStudyModeSessionId;

        @NotBlank(message = "Second session is required")
        @ValidUuid(message = "Invalid Study mode session mode format")
        private String toStudyModeSessionId;

        @JsonIgnore
        @AssertTrue(message = "Study mode session must be different; a study mode session can not prohibit transfer to itself")
        public boolean isSessionTransferValid() {
            if (fromStudyModeSessionId == null || toStudyModeSessionId == null) {
                return true;
            }
            return !fromStudyModeSessionId.equals(toStudyModeSessionId);
        }


}
