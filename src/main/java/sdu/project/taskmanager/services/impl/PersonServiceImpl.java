package sdu.project.taskmanager.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sdu.project.taskmanager.exceptions.ResourceMappingException;
import sdu.project.taskmanager.exceptions.ResourceNotFoundException;
import sdu.project.taskmanager.models.user.Person;
import sdu.project.taskmanager.models.user.Role;
import sdu.project.taskmanager.repositories.PersonRepository;
import sdu.project.taskmanager.services.PersonService;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Person getById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceMappingException("Person not found."));
    }

    @Override
    public Person getByUsername(String username) {
        return personRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceMappingException("Person not found."));
    }

    @Override
    @Transactional
    public Person update(Person person) {
        if (!person.getPassword().equals(person.getPasswordConfirmation())){
            throw new IllegalStateException("Password and password confirmation not equals.");
        }
        Person existedPerson = getById(person.getId());

        existedPerson.setUsername(person.getUsername());
        existedPerson.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(existedPerson);
    }

    @Override
    @Transactional
    public Person create(Person person) {
        if (personRepository.findByUsername(person.getUsername()).isPresent()){
            throw new IllegalStateException("Person already exists.");
        }
        if (!person.getPassword().equals(person.getPasswordConfirmation())){
            throw new IllegalStateException("Password and password confirmation not equals.");
        }

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        person.setRoles(roles);
        personRepository.save(person);

        return person;
    }

    @Override
    public boolean isTaskOwner(Long personId, Long taskId) {
        return personRepository.isTaskOwner(personId, taskId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
