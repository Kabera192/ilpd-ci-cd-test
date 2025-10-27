package rw.ac.ilpd.sharedlibrary.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseDetailWrapper <C,D>{
    private C content;
    private D data;

}
