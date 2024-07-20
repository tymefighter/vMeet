package com.vmeet.graphql;

import com.vmeet.data.user.CreateUserDTO;
import com.vmeet.data.user.UserDTO;
import com.vmeet.data.user.UserRepository;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@GraphQLApi
@Service
public class UserGraphQLAPI {
  final UserRepository userRepository;

  @Autowired
  public UserGraphQLAPI(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GraphQLQuery(name = "createUser")
  public UserDTO createUser(CreateUserDTO createUserDTO) {
    return userRepository.createUser(createUserDTO);
  }
}
