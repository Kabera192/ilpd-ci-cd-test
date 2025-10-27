package rw.ac.ilpd.mis.shared.dto.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorDetail<T>
{
    private String error;
    private T request;
}
