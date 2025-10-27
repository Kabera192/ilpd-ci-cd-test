package rw.ac.ilpd.mis.shared.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.mis.shared.dto.module.ModuleResponse;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserModuleDetailResponse {
        private UserResponse user;
        private ModuleResponse module;
}
