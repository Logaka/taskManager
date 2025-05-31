package sdu.project.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sdu.project.taskmanager.models.task.Status;
import sdu.project.taskmanager.models.task.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = """
                select * from tasks t 
                where t.admin_id = :userId or t.executor_id = :userId
            """, nativeQuery = true)
    List<Task> findAllByPersonId(@Param("userId") Long userId);


    @Query(value = """
        select * from tasks t 
        where (t.admin_id = :person_id or t.executor_id = :person_id) and t.status = :status
        """, nativeQuery = true)
    List<Task> findAllByPersonIdAndStatus(@Param("person_id") Long personId, @Param("status") String status);
}
