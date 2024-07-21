package com.vmeet.graphql;

import com.vmeet.data.user.CreateUserDTO;
import com.vmeet.data.user.UpdateUserDTO;
import com.vmeet.data.user.UserDTO;
import com.vmeet.data.user.UserRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@GraphQLApi
@Service
public class UserGraphQLAPI {
  final UserRepository userRepository;

  @Autowired
  public UserGraphQLAPI(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GraphQLMutation(name = "createUser")
  public UserDTO createUser(@GraphQLNonNull CreateUserDTO createUserDTO) {
    return userRepository.createUser(createUserDTO);
  }

  @GraphQLMutation(name = "updateUser")
  public UserDTO updateUser(@GraphQLNonNull UpdateUserDTO updateUserDTO) {
    return userRepository.updateUser(updateUserDTO);
  }

  @GraphQLQuery(name = "getUsers")
  public List<UserDTO> getUsers() {
    return userRepository.getUsers();
  }
}
