package com.vmeet.authentication;

public class LoginResponse {
  private final boolean success;
  private final String sessionId;

  public static LoginResponse failed() {
    return new LoginResponse(false, null);
  }

  public static LoginResponse successful(String sessionId) {
    return new LoginResponse(true, sessionId);
  }

  public LoginResponse(boolean success, String sessionId) {
    this.success = success;
    this.sessionId = sessionId;
  }

  public boolean wasSuccessful() {
    return success;
  }

  public String getSessionId() {
    return sessionId;
  }
}
