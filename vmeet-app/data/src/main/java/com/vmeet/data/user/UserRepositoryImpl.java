package com.vmeet.data.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

  MongoTemplate mongoTemplate;

  @Autowired
  public UserRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public UserDTO createUser(CreateUserDTO createUserDTO) {
    User user = new User().username(createUserDTO.getUsername()).name(createUserDTO.getName()).emailAddress(createUserDTO.getEmailAddress()).phoneNumber(createUserDTO.getPhoneNumber()).passwordHash(UserUtils.hashPassword(createUserDTO.getPassword()));

    User createdUser = mongoTemplate.save(user);
    return new UserDTO(createdUser);
  }

  @Override
  public UserDTO updateUser(UpdateUserDTO updateUserDTO) {
    if (updateUserDTO.getId() == null) {
      return null;
    }

    Criteria criteria = Criteria.where(User.ID_FIELD).is(updateUserDTO.getId());

    Update update = new Update();
    if (updateUserDTO.getName() != null) {
      update.set(User.NAME_FIELD, updateUserDTO.getName());
    }

    if (updateUserDTO.getEmailAddress() != null) {
      update.set(User.EMAIL_ADDRESS_FIELD, updateUserDTO.getEmailAddress());
    }

    if (updateUserDTO.getPhoneNumber() != null) {
      update.set(User.PHONE_NUMBER_FIELD, updateUserDTO.getPhoneNumber());
    }

    if (updateUserDTO.getPassword() != null) {
      update.set(User.PASSWORD_HASH_FIELD, UserUtils.hashPassword(updateUserDTO.getPassword()));
    }

    User updatedUser = mongoTemplate.findAndModify(Query.query(criteria), update, new FindAndModifyOptions().returnNew(true), User.class);
    return updatedUser == null ? null : new UserDTO(updatedUser);
  }

  @Override
  public List<UserDTO> getUsers() {
    return mongoTemplate.findAll(User.class).stream().map(UserDTO::new).toList();
  }
}
