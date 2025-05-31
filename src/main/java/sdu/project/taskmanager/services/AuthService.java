package sdu.project.taskmanager.services;

import sdu.project.taskmanager.dto.auth.JwtRequest;
import sdu.project.taskmanager.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest request);

    JwtResponse refresh(String refreshToken);
}
