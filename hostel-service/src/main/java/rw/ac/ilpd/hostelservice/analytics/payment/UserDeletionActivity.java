package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
//@Schema(description = "User deletion activity")
public class UserDeletionActivity {
//    @Schema(description = "User ID")
    private String userId;

//    @Schema(description = "Number of deletions", example = "12")
    private Long deletionCount;

//    @Schema(description = "Last deletion date")
    private LocalDateTime lastDeletion;
}