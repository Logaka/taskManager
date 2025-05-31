package sdu.project.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import sdu.project.taskmanager.models.user.Role;
import sdu.project.taskmanager.validaion.OnCreate;
import sdu.project.taskmanager.validaion.OnUpdate;

import java.util.Set;

@Data
public class PersonDto {

    @NotNull(message = "id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotBlank(message = "username must be filled.", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 6, max = 255, message = "username length must be between 6-255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "password must be filled.", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 6, max = 255, message = "password length must be between 6-255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "password confirmation must be filled.", groups = {OnCreate.class, OnUpdate.class})
    private String passwordConfirmation;

}
