package sdu.project.taskmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sdu.project.taskmanager.dto.PersonDto;
import sdu.project.taskmanager.dto.TaskDto;
import sdu.project.taskmanager.dto.mappers.PersonMapper;
import sdu.project.taskmanager.dto.mappers.TaskMapper;
import sdu.project.taskmanager.models.task.Status;
import sdu.project.taskmanager.models.task.Task;
import sdu.project.taskmanager.models.user.Person;
import sdu.project.taskmanager.services.PersonService;
import sdu.project.taskmanager.services.TaskService;
import sdu.project.taskmanager.validaion.OnCreate;
import sdu.project.taskmanager.validaion.OnUpdate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/persons")
public class PersonController {

    private final PersonService personService;
    private final TaskService taskService;

    private final PersonMapper personMapper;
    private final TaskMapper taskMapper;


    @PutMapping
    @PreAuthorize("@customSecurityExpression.canAccessPerson(#dto.id)")
    public ResponseEntity<PersonDto> updatePerson(@Validated(OnUpdate.class) @RequestBody PersonDto dto) {
        Person person = personMapper.toEntity(dto);
        Person updatedPerson = personService.update(person);

        return ResponseEntity.ok(personMapper.toDto(updatedPerson));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessPerson(#id)")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable("id") Long id) {
        Person person = personService.getById(id);
        return ResponseEntity.ok(personMapper.toDto(person));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessPerson(#id)")
    public void deletePerson(@PathVariable Long id) {
        personService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    @PreAuthorize("@customSecurityExpression.canAccessPerson(#id)")
    public ResponseEntity<List<TaskDto>> getTasksByUserId(
            @PathVariable Long id,
            @RequestParam(name = "status", required = false ) Status status
    ) {
        List<Task> tasks = taskService.getAllByUserId(id, status);
        List<TaskDto> dtoList = taskMapper.toDto(tasks);

        return ResponseEntity.ok(dtoList);
    }


}
