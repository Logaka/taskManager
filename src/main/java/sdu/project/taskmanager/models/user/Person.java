package sdu.project.taskmanager.models.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.Data;
import sdu.project.taskmanager.models.task.Task;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    private Long id;

    @Column(unique = true)
    private String username;


    private String password;

    @Transient
    private String passwordConfirmation;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "persons_roles")
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;


    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "executor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Task> assignedTasks;
}
