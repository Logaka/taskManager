package sdu.project.taskmanager.dto.mappers;

import org.mapstruct.Mapper;
import sdu.project.taskmanager.dto.PersonDto;
import sdu.project.taskmanager.models.user.Person;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDto toDto(Person person);

    Person toEntity(PersonDto dto);
}
