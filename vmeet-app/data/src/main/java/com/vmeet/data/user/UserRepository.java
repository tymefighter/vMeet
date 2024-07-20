package com.vmeet.data.user;

import java.util.List;

public interface UserRepository {
  UserDTO createUser(CreateUserDTO createUserDTO);

  UserDTO updateUser(UpdateUserDTO userDTO);

  List<UserDTO> getUsers();
}
