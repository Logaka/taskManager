package sdu.project.taskmanager.services;

import sdu.project.taskmanager.models.task.Status;
import sdu.project.taskmanager.models.task.Task;

import java.util.List;

public interface TaskService {

    Task create(Task task, Long adminId, Long executorId);

    Task getById(Long id);

    Task update(Task task, Long executorId);

    void delete(Long id);

    List<Task> getAllByUserId(Long id, Status status);
}
