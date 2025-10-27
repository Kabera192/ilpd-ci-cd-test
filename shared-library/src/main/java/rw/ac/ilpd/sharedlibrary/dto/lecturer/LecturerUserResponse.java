package rw.ac.ilpd.sharedlibrary.dto.lecturer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.user.UserResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturerUserResponse {
    private LecturerResponse lecturer;
    private UserResponse user;
}
