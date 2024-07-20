package com.vmeet.data.user;

public class UserDTO {
  private String id;
  private String username;
  private String name;
  private String emailAddress;
  private String phoneNumber;

  public UserDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.name = user.getName();
    this.emailAddress = user.getEmailAddress();
    this.phoneNumber = user.getPhoneNumber();
  }

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
}
