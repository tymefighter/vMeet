package com.vmeet.infra;

public class UserContextProvider {
  public static class UserContext {
    private static String userId;

    public static String getUserId() {
      return userId;
    }

    public static void setUserId(String userId) {
      UserContext.userId = userId;
    }
  }

  private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

  public static UserContext getUserContext() {
    return userContext.get();
  }
}
