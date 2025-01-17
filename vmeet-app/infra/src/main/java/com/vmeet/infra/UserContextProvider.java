package com.vmeet.infra;

public class UserContextProvider {
  private UserContextProvider() {}

  public static class UserContext {
    private String userId;

    private UserContext() {}

    public String getUserId() {
      return userId;
    }
  }

  private static final ThreadLocal<UserContext> userContext = ThreadLocal.withInitial(UserContext::new);

  public static UserContext getUserContext() {
    return userContext.get();
  }

  public static void setUserId(String userId) {
    userContext.get().userId = userId;
  }
}
