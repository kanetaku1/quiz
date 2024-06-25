package main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;

public class UserManager {
  private static Map<Session, User> userMap = new ConcurrentHashMap<>();

  public static void addUser(Session session, User user) {
      userMap.put(session, user);
  }

  public static void removeUser(Session session) {
      userMap.remove(session);
  }

  public static User getUser(Session session) {
      return userMap.get(session);
  }
}