package rw.ac.ilpd.sharedlibrary.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.module.ModuleResponse;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserModuleDetailResponse {
        private UserResponse user;
        private ModuleResponse module;
}
