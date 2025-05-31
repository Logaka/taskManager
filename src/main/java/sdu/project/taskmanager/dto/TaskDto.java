package sdu.project.taskmanager.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import sdu.project.taskmanager.models.task.Status;
import sdu.project.taskmanager.validaion.OnCreate;
import sdu.project.taskmanager.validaion.OnUpdate;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    @NotNull(message = "id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotBlank(message = "title must be filled.", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "title must be smaller than 255 symbols.")
    private String title;

    private String description;

    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;

}
