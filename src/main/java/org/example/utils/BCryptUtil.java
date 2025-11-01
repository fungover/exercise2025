package org.example.utils;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {

  public static String hashPassword(String password) {
    String salt = BCrypt.gensalt(10);
    return BCrypt.hashpw(password, salt);
  }

  public static boolean checkPassword(String plainPassword, String hashedPassword) {
    return BCrypt.checkpw(plainPassword, hashedPassword);
  }
}
