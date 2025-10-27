/**
 * A simple response DTO indicating success or failure and an associated message.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.simpleresponse;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleResponse {
    private boolean success;
    private String message;
}
