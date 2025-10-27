package rw.ac.ilpd.mis.shared.dto.unit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 18/07/2025
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Unit {

        private String id;

        @NotNull(message = "Name is required")
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 150, message = "Name cannot exceed 150 characters")
        private String name;

        @NotNull(message = "Acronym is required")
        @NotBlank(message = "Acronym cannot be blank")
        @Size(max = 10, message = "Acronym cannot exceed 10 characters")
        private String acronym;

        @NotNull(message = "Description is required")
        @NotBlank(message = "Description cannot be blank")
        private String description;

        @Override
        public String toString() {
                return "Unit{" +
                        "unitId=" + id +
                        ", name='" + name + '\'' +
                        ", acronym='" + acronym + '\'' +
                        ", description='" + description + '\'' +
                        '}';
        }

}
