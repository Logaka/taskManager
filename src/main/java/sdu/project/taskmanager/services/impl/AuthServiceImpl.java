package sdu.project.taskmanager.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import sdu.project.taskmanager.dto.auth.JwtRequest;
import sdu.project.taskmanager.dto.auth.JwtResponse;
import sdu.project.taskmanager.models.user.Person;
import sdu.project.taskmanager.security.JwtTokenProvider;
import sdu.project.taskmanager.services.AuthService;
import sdu.project.taskmanager.services.PersonService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PersonService personService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(JwtRequest request) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        Person person = personService.getByUsername(request.getUsername());
        jwtResponse.setId(person.getId());
        jwtResponse.setUsername(person.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(person.getId(), person.getUsername(), person.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(person.getId(), person.getUsername()));
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
