package rw.ac.ilpd.mis.shared.dto.hostel;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class HostelRoomTypePricingRequest {
    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @NotBlank(message = "Payment period is required")
    @Pattern(regexp = "DAY|MONTH", message = "Payment period must be either 'DAY' or 'MONTH'")
    private String paymentPeriod;

    @NotBlank(message = "Client type must not be blank")
    @Pattern(regexp = "STUDENT|OFFICER|EXTERNAL_USER", message = "Client type must be 'STUDENT', 'OFFICER', or 'EXTERNAL_USER'")
    private String clientType;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    // Getters and setters (or use Lombok @Data if desired)
}
