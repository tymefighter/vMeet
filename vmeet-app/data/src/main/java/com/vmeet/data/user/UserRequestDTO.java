package com.vmeet.data.user;

public class UserRequestDTO {
  private String id;
  private String username;
  private String name;
  private String emailAddress;
  private String phoneNumber;
  private String password;

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserRequestDTO id(String id) {
    this.id = id;
    return this;
  }

  public UserRequestDTO username(String username) {
    this.username = username;
    return this;
  }

  public UserRequestDTO name(String name) {
    this.name = name;
    return this;
  }

  public UserRequestDTO emailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  public UserRequestDTO phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public UserRequestDTO password(String password) {
    this.password = password;
    return this;
  }
}
