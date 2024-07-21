package com.vmeet.graphql;

import com.vmeet.authentication.AuthService;
import com.vmeet.authentication.LoginRequestDTO;
import com.vmeet.data.user.UserRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@GraphQLApi
@Service
public class AuthenticationGraphQLAPI {

  private final UserRepository userRepository;
  private final AuthService authService;

  @Autowired
  public AuthenticationGraphQLAPI(UserRepository userRepository, AuthService authService) {
    this.userRepository = userRepository;
    this.authService = authService;
  }

  @GraphQLMutation(name = "login")
  public boolean login(@GraphQLNonNull LoginRequestDTO loginRequestDTO) {
    return authService.login(loginRequestDTO);
  }

  @GraphQLMutation(name = "logout")
  public boolean logout() {
    return authService.logout();
  }
}
