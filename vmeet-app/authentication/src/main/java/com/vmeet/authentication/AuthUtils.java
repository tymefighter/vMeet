package com.vmeet.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.UUID;

public class AuthUtils {

  private static final String SESSION_ID_COOKIE_NAME = "sessionId";

  public static String createRandomSessionId() {
    return UUID.randomUUID().toString();
  }

  public static String getSessionId(HttpServletRequest servletRequest) {
    return Arrays.stream(servletRequest.getCookies())
        .filter(cookie -> cookie.getName().equals(SESSION_ID_COOKIE_NAME))
        .findFirst()
        .map(Cookie::getValue)
        .orElse(null);
  }

  public static Cookie createSessionIdCookie(String sessionId) {
    return new Cookie(SESSION_ID_COOKIE_NAME, sessionId);
  }
}
