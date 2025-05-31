package sdu.project.taskmanager.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sdu.project.taskmanager.exceptions.ResourceNotFoundException;
import sdu.project.taskmanager.models.task.Status;
import sdu.project.taskmanager.models.task.Task;
import sdu.project.taskmanager.models.user.Person;
import sdu.project.taskmanager.repositories.PersonRepository;
import sdu.project.taskmanager.repositories.TaskRepository;
import sdu.project.taskmanager.services.PersonService;
import sdu.project.taskmanager.services.TaskService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final PersonService personService;


    @Override
    @Transactional
    public Task create(Task task, Long adminId, Long executorId) {
        Person admin = personService.getById(adminId);
        Person executor = personService.getById(executorId);

        if (task.getStatus() == null)
            task.setStatus(Status.TODO);

        task.setAdmin(admin);
        task.setExecutor(executor);
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));
    }


    @Override
    @Transactional
    public Task update(Task task, Long executorId) {
        Task existingTask = getById(task.getId());
        Person person = personService.getById(executorId);

        if (task.getStatus() != null){
            existingTask.setStatus(task.getStatus());
        }
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setExecutor(person);

        return taskRepository.save(existingTask);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAllByUserId(Long id, Status status) {
        if (status != null)
            return taskRepository.findAllByPersonIdAndStatus(id, status.name());
        return taskRepository.findAllByPersonId(id);
    }
}
