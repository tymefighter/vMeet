package com.vmeet.authentication;

import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
  @Override
  public boolean login(LoginRequestDTO loginRequestDTO) {
    return false;
  }

  @Override
  public boolean logout() {
    return false;
  }
}
