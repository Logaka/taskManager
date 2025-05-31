package sdu.project.taskmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sdu.project.taskmanager.dto.TaskDto;
import sdu.project.taskmanager.dto.mappers.PersonMapper;
import sdu.project.taskmanager.dto.mappers.TaskMapper;
import sdu.project.taskmanager.models.task.Task;
import sdu.project.taskmanager.models.user.Person;
import sdu.project.taskmanager.services.PersonService;
import sdu.project.taskmanager.services.TaskService;
import sdu.project.taskmanager.validaion.OnCreate;
import sdu.project.taskmanager.validaion.OnUpdate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessTask(#id)")
    public ResponseEntity<TaskDto> getById(@PathVariable("id") Long id) {
        Task task = taskService.getById(id);
        return ResponseEntity.ok(taskMapper.toDto(task));
    }

    @PutMapping
    @PreAuthorize("@customSecurityExpression.canAccessTask(#dto.id)")
    public ResponseEntity<TaskDto> updateTask(@Validated(OnUpdate.class)
                                              @RequestBody TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task updatedTask = taskService.update(task, dto.getExecutor_id());

        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessTask(#id)")
    public void deleteById(@PathVariable("id") Long id) {
        taskService.delete(id);
    }


    @PostMapping("/create")
    public ResponseEntity<TaskDto> create(@Validated(OnCreate.class) @RequestBody TaskDto dto){
        Task task = taskMapper.toEntity(dto);
        Task savedTask = taskService.create(task, dto.getAdmin_id(), dto.getExecutor_id());
        TaskDto taskDto = taskMapper.toDto(savedTask);
        return ResponseEntity.ok(taskDto);
    }
}
