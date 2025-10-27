/**
 * ApplicationDeferringCommentDto.java
 * <p>
 * Description:
 * Data Transfer Object (DTO) representing a comment made when deferring an application.
 * <p>
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-04
 */
package rw.ac.ilpd.mis.shared.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDeferringCommentResponse {
    private String id;
    private String content;
    private String deferredDocId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}

