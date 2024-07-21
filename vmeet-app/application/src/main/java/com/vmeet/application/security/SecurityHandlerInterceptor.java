package com.vmeet.application.security;

import com.vmeet.authentication.AuthService;
import com.vmeet.authentication.AuthUtils;
import com.vmeet.authentication.ValidateSessionResponseDTO;
import com.vmeet.infra.UserContextProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

@Component
public class SecurityHandlerInterceptor implements HandlerInterceptor {

  private static final Set<String> ALLOWABLE_URLS = Set.of("/rest/login", "/error");

  private final AuthService authService;

  public SecurityHandlerInterceptor(AuthService authService) {
    this.authService = authService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    if (ALLOWABLE_URLS.contains(request.getRequestURI())) {
      return true;
    }

    String sessionId = AuthUtils.getSessionId(request);
    ValidateSessionResponseDTO validateSessionResponseDTO = authService.validateSession(sessionId);

    if (!validateSessionResponseDTO.isSessionValid()) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return false;
    }

    UserContextProvider.setUserId(validateSessionResponseDTO.getUserId());
    return true;
  }
}
