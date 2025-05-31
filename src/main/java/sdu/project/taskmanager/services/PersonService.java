package sdu.project.taskmanager.services;

import sdu.project.taskmanager.models.user.Person;

public interface PersonService {

    Person getById(Long id);

    Person getByUsername(String username);

    Person update(Person person);

    Person create(Person person);

    boolean isTaskOwner(Long personId, Long taskId);

    void delete(Long id);
}
