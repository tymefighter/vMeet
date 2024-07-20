package com.vmeet.authentication;

public interface AuthService {
  boolean login(LoginRequestDTO loginRequestDTO);

  boolean logout();
}
