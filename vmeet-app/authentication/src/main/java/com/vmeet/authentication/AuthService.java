package com.vmeet.authentication;

public interface AuthService {
  LoginResponse login(LoginRequestDTO loginRequestDTO);

  boolean logout(LogoutRequestDTO logoutRequestDTO);

  boolean logoutAllSessions(LogoutRequestDTO logoutRequestDTO);

  ValidateSessionResponseDTO validateSession(String sessionId);
}
