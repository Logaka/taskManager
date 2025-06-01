package sdu.project.taskmanager.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import sdu.project.taskmanager.dto.auth.JwtRequest;
import sdu.project.taskmanager.dto.auth.JwtResponse;
import sdu.project.taskmanager.models.user.Person;
import sdu.project.taskmanager.models.user.Role;
import sdu.project.taskmanager.security.JwtTokenProvider;
import sdu.project.taskmanager.services.PersonService;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PersonService personService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void login_mustLoginUser(){
        //given

        String username = "username";
        String password = "password";
        String mockAccessToken = "mockAccessToken";
        String mockRefreshToken = "mockRefreshToken";

        Person mockPerson = new Person();
        mockPerson.setId(1L);
        mockPerson.setUsername(username);
        mockPerson.setPassword(password);
        mockPerson.setRoles(Set.of(Role.ROLE_USER));

        JwtRequest request = new JwtRequest();
        request.setUsername(username);
        request.setPassword(password);

        doReturn(mockPerson).when(personService).getByUsername(username);
        when(jwtTokenProvider.createAccessToken(mockPerson.getId(), mockPerson.getUsername(), mockPerson.getRoles()))
                .thenReturn(mockAccessToken);
        when(jwtTokenProvider.createRefreshToken(mockPerson.getId(), mockPerson.getUsername()))
                .thenReturn(mockRefreshToken);

        //when
        JwtResponse response = authService.login(request);

        //then
        assertEquals(mockPerson.getId(), response.getId());
        assertEquals(mockPerson.getUsername(), response.getUsername());
        assertEquals(mockAccessToken, response.getAccessToken());
        assertEquals(mockRefreshToken, response.getRefreshToken());

        verify(personService).getByUsername(mockPerson.getUsername());
        verify(jwtTokenProvider).createAccessToken(mockPerson.getId(), mockPerson.getUsername(), mockPerson.getRoles());
        verify(jwtTokenProvider).createRefreshToken(mockPerson.getId(), mockPerson.getUsername());
    }
}