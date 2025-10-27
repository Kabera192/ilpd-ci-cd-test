package rw.ac.ilpd.sharedlibrary.dto.curriculummodule;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumModuleCurriculumResponse
{
    private String id;
    private String moduleId;
    private Integer moduleOrder;
    private Integer credits;
}
