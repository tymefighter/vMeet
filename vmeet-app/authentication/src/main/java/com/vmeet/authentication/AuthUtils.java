package com.vmeet.authentication;

import java.util.UUID;

public class AuthUtils {

  public static String createRandomSessionId() {
    return UUID.randomUUID().toString();
  }

}
