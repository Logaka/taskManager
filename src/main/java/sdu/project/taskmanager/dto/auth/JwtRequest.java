package sdu.project.taskmanager.dto.auth;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JwtRequest {

    @NotBlank(message = "username must be filled.")
    private String username;

    @NotBlank(message = "password must be filled.")
    private String password;
}
