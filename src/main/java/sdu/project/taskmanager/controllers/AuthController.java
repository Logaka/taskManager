package sdu.project.taskmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sdu.project.taskmanager.dto.PersonDto;
import sdu.project.taskmanager.dto.auth.JwtRequest;
import sdu.project.taskmanager.dto.auth.JwtResponse;
import sdu.project.taskmanager.dto.mappers.PersonMapper;
import sdu.project.taskmanager.models.user.Person;
import sdu.project.taskmanager.services.AuthService;
import sdu.project.taskmanager.services.PersonService;
import sdu.project.taskmanager.validaion.OnCreate;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;
    private final PersonService personService;

    private final PersonMapper personMapper;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Validated @RequestBody JwtRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<PersonDto> register(@Validated(OnCreate.class) @RequestBody PersonDto personDto){
        Person person = personMapper.toEntity(personDto);
        Person createdPerson =  personService.create(person);
        return ResponseEntity.ok(personMapper.toDto(createdPerson));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody  String refreshToken){
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }
}
