package com.vmeet.data.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class User {
  @Id
  private String id;
  private String username;
  private String passwordHash;
  private String name;
  private String emailAddress;
  private String phoneNumber;

  public static String ID_FIELD = "_id";
  public static String USERNAME_FIELD = "username";
  public static String PASSWORD_HASH_FIELD = "passwordHash";
  public static String NAME_FIELD = "name";
  public static String EMAIL_ADDRESS_FIELD = "emailAddress";
  public static String PHONE_NUMBER_FIELD = "phoneNumber";

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public User username(String username) {
    this.username = username;
    return this;
  }

  public User passwordHash(String passwordHash) {
    this.passwordHash = passwordHash;
    return this;
  }

  public User name(String name) {
    this.name = name;
    return this;
  }

  public User emailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  public User phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }
}
