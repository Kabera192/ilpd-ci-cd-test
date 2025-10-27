// MultipleAcademicBackgroundRequest.java - For creating multiple academic backgrounds in one request
package rw.ac.ilpd.sharedlibrary.dto.application;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultipleAcademicBackgroundRequest {

    @NotEmpty(message = "Academic backgrounds cannot be empty")
    @Valid
    private List<AcademicBackgroundRequest> academicBackgrounds;
}