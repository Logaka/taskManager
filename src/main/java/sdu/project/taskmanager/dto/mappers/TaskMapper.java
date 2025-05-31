package sdu.project.taskmanager.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import sdu.project.taskmanager.dto.TaskDto;
import sdu.project.taskmanager.models.task.Task;
import sdu.project.taskmanager.models.user.Person;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "admin_id", source = "admin", qualifiedByName = "personToId")
    @Mapping(target = "executor_id", source = "executor", qualifiedByName = "personToId")
    TaskDto toDto(Task task);

    Task toEntity(TaskDto dto);

    List<TaskDto> toDto(List<Task> task);

    List<Task> toEntities(List<TaskDto> dto);

    @Named("personToId")
    default Long personToId(Person person){
        return  (person == null) ? null : person.getId();
    }
}
