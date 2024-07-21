package com.vmeet.application;

import com.vmeet.authentication.AuthService;
import com.vmeet.authentication.AuthUtils;
import com.vmeet.authentication.ValidateSessionResponseDTO;
import com.vmeet.infra.UserContextProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final AuthService authService;

  public SecurityConfig(AuthService authService) {
    this.authService = authService;
  }

  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.addFilter((request, response, chain) -> {
      if (request instanceof HttpServletRequest httpRequest) {
        if (httpRequest.getRequestURL().toString().equals("/login")) {
          return;
        }

        String sessionId = AuthUtils.getSessionId(httpRequest);
        ValidateSessionResponseDTO validateSessionResponseDTO = authService.validateSession(sessionId);

        if (!validateSessionResponseDTO.isSessionValid()) {
          throw new ServletException("Unauthenticated");
        }

        UserContextProvider.setUserId(validateSessionResponseDTO.getUserId());
      }
    }).build();
  }
}
