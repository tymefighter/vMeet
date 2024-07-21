package com.vmeet.authentication;

public class LogoutRequestDTO {
  private final String sessionId;
  private final String userId;

  public static LogoutRequestDTO fromSessionId(String sessionId) {
    return new LogoutRequestDTO(sessionId, null);
  }

  public static LogoutRequestDTO fromUserId(String userId) {
    return new LogoutRequestDTO(null, userId);
  }

  public LogoutRequestDTO(String sessionId, String userId) {
    this.sessionId = sessionId;
    this.userId = userId;
  }

  public String getSessionId() {
    return sessionId;
  }

  public String getUserId() {
    return userId;
  }
}
