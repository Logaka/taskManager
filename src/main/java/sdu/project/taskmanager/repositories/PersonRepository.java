package sdu.project.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sdu.project.taskmanager.models.user.Person;
import sdu.project.taskmanager.models.user.Role;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);

    @Query(value = """
            select exists(
                select 1
                from tasks t
                where (t.admin_id = :personId or t.executor_id = :personId)
                and t.id = :taskId
            )
            """, nativeQuery = true)
    boolean isTaskOwner(@Param("personId") Long personId, @Param("taskId") Long taskId);

}
