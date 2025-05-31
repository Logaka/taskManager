package sdu.project.taskmanager.security.expressions;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sdu.project.taskmanager.models.user.Role;
import sdu.project.taskmanager.security.PersonDetails;
import sdu.project.taskmanager.services.PersonService;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final PersonService personService;

    public boolean canAccessPerson(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Long personId = personDetails.getPerson().getId();

        return personId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(Authentication authentication, Role... roles){
        for (Role role: roles){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());

            if (authentication.getAuthorities().contains(authority))
                return true;
        }
        return false;
    }

    public boolean canAccessTask(Long taskId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Long personId = personDetails.getPerson().getId();

        return personService.isTaskOwner(personId, taskId);
    }
}
