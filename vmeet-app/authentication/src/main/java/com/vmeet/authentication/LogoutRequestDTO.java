package com.vmeet.authentication;

public class LogoutRequestDTO {
  private String sessionId;

  public LogoutRequestDTO(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getSessionId() {
    return sessionId;
  }
}
