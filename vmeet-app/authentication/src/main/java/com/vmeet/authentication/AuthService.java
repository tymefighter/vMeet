package com.vmeet.authentication;

public interface AuthService {
  LoginResponse login(LoginRequestDTO loginRequestDTO);

  boolean logout(LogoutRequestDTO logoutRequestDTO);
}
