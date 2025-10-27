package rw.ac.ilpd.sharedlibrary.dto.intake;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRequiredDocIntakeResponse
{
    private UUID id;
    private UUID requiredDocNameId;
    private boolean isRequired;
}
