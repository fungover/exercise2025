package org.example.utils;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {

  public static String hashPassword(String password) {
    if (password == null) {
      throw new IllegalArgumentException("Password cannot be null");
      }
    String salt = BCrypt.gensalt(10);
    return BCrypt.hashpw(password, salt);
  }

  public static boolean checkPassword(String plainPassword, String hashedPassword) {
    if (plainPassword == null || hashedPassword == null) {
      return false;
      }
    return BCrypt.checkpw(plainPassword, hashedPassword);
  }
}
