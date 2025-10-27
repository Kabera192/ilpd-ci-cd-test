package rw.ac.ilpd.mis.shared.dto.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentDetailResponse {
    private DocumentResponse document;
    private DocumentTypeResponse documentType;
}
