package main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
  private static Map<String, User> userMap = new ConcurrentHashMap<>();

  public static void addUser(String sessionId, User user) {
      userMap.put(sessionId, user);
  }

  public static void removeUser(String sessionId) {
      userMap.remove(sessionId);
  }

  public static User getUser(String sessionId) {
      return userMap.get(sessionId);
  }
}