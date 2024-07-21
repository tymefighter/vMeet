package com.vmeet.rest;

import com.vmeet.authentication.AuthService;
import com.vmeet.authentication.AuthUtils;
import com.vmeet.authentication.LoginRequestDTO;
import com.vmeet.authentication.LoginResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class AuthenticationRestAPI {

  private final AuthService authService;

  public AuthenticationRestAPI(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public void login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse httpResponse) {
    LoginResponse loginResponse = authService.login(loginRequestDTO);

    if (!loginResponse.wasSuccessful()) {
      httpResponse.setStatus(401);
      return;
    }

    httpResponse.addCookie(AuthUtils.createSessionIdCookie(loginResponse.getSessionId()));
    httpResponse.setStatus(200);
  }

}
