package com.vmeet.graphql;

import com.vmeet.authentication.*;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLRootContext;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.autoconfigure.DefaultGlobalContext;
import jakarta.servlet.http.HttpServletRequest;
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

  @GraphQLMutation(name = "logout")
  public boolean logout(@GraphQLRootContext DefaultGlobalContext<NativeWebRequest> context) {
    HttpServletRequest servletRequest = context.getNativeRequest().getNativeRequest(HttpServletRequest.class);
    String sessionId = servletRequest == null ? null : AuthUtils.getSessionId(servletRequest);

    return sessionId != null && authService.logout(LogoutRequestDTO.fromSessionId(sessionId));
  }
}
