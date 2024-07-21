package com.vmeet.graphql;

import com.vmeet.authentication.AuthService;
import com.vmeet.authentication.LoginRequestDTO;
import com.vmeet.authentication.LoginResponse;
import com.vmeet.authentication.LogoutRequestDTO;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLRootContext;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.autoconfigure.DefaultGlobalContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;

@GraphQLApi
@Service
public class AuthenticationGraphQLAPI {

  private static final String SESSION_ID_COOKIE_NAME = "sessionId";

  private final AuthService authService;

  @Autowired
  public AuthenticationGraphQLAPI(AuthService authService) {
    this.authService = authService;
  }

  @GraphQLMutation(name = "login")
  public boolean login(@GraphQLNonNull LoginRequestDTO loginRequestDTO,
                       @GraphQLRootContext DefaultGlobalContext<NativeWebRequest> context) {
    LoginResponse loginResponse = authService.login(loginRequestDTO);
    HttpServletResponse servletResponse = context.getNativeRequest().getNativeResponse(HttpServletResponse.class);

    if (loginResponse.wasSuccessful() && servletResponse != null) {
      servletResponse.addCookie(new Cookie(SESSION_ID_COOKIE_NAME, loginResponse.getSessionId()));
      return true;
    }

    return false;
  }

  @GraphQLMutation(name = "logout")
  public boolean logout(@GraphQLRootContext DefaultGlobalContext<NativeWebRequest> context) {
    HttpServletRequest servletRequest = context.getNativeRequest().getNativeRequest(HttpServletRequest.class);
    String sessionId = servletRequest == null ? null : getSessionId(servletRequest);

    return sessionId != null && authService.logout(new LogoutRequestDTO(sessionId));
  }

  private String getSessionId(HttpServletRequest servletRequest) {
    return Arrays.stream(servletRequest.getCookies())
        .filter(cookie -> cookie.getName().equals(SESSION_ID_COOKIE_NAME))
        .findFirst()
        .map(Cookie::getValue)
        .orElse(null);
  }
}
