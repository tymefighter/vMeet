package com.vmeet.data.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class UserUtils {
  private static final String HASH_ALGORITHM = "SHA-256";

  public static String hashPassword(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
      byte[] hashedBytes = md.digest(password.getBytes());

      return bytesToHex(hashedBytes);
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new RuntimeException(noSuchAlgorithmException);
    }
  }

  private static String bytesToHex(byte[] bytes) {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte _byte : bytes) {
      stringBuilder.append(String.format("%02x", _byte));
    }

    return stringBuilder.toString();
  }
}
