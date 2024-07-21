package com.vmeet.graphql;

import com.vmeet.authentication.*;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLRootContext;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.autoconfigure.DefaultGlobalContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

@GraphQLApi
@Service
public class AuthenticationGraphQLAPI {

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
      servletResponse.addCookie(AuthUtils.createSessionIdCookie(loginResponse.getSessionId()));
      return true;
    }

    return false;
  }

  @GraphQLMutation(name = "logout")
  public boolean logout(@GraphQLRootContext DefaultGlobalContext<NativeWebRequest> context) {
    HttpServletRequest servletRequest = context.getNativeRequest().getNativeRequest(HttpServletRequest.class);
    String sessionId = servletRequest == null ? null : AuthUtils.getSessionId(servletRequest);

    return sessionId != null && authService.logout(LogoutRequestDTO.fromSessionId(sessionId));
  }
}
