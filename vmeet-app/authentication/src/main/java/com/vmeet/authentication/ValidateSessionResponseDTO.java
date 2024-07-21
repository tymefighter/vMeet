package com.vmeet.authentication;

public class ValidateSessionResponseDTO {
  private final boolean isSessionValid;
  private final String userId;

  public static ValidateSessionResponseDTO invalidSession() {
    return new ValidateSessionResponseDTO(false, null);
  }

  public static ValidateSessionResponseDTO validSession(String userId) {
    return new ValidateSessionResponseDTO(true, userId);
  }

  public ValidateSessionResponseDTO(boolean isSessionValid, String userId) {
    this.isSessionValid = isSessionValid;
    this.userId = userId;
  }

  public boolean isSessionValid() {
    return isSessionValid;
  }

  public String getUserId() {
    return userId;
  }
}
