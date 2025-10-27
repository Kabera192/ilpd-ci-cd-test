/**
 * ApplicationDeferringCommentDto.java
 * <p>
 * Description:
 * Data Transfer Object (DTO) representing a comment made when deferring an application.
 * <p>
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-04
 */
package rw.ac.ilpd.sharedlibrary.dto.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDeferringCommentRequest {

    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be blank")
    @Size(min = 5, max = 1000, message = "Comment must be between 5 and 1000 characters")
    @RestrictedString
    private String content;

    @NotNull(message = "Deferred Document ID cannot be null")
    @NotBlank(message = "Deferred Document ID cannot be blank")
    @RestrictedString
    private String deferredDocId;
}
