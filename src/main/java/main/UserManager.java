package main;

import java.util.HashMap;
import java.util.Map;

import main.User.UserType;

public class UserManager {
  private static Map<String, User> userMap = new HashMap<>();

    // ユーザーを追加するメソッド
    public static void addUser(String sessionId, String username, UserType userType) {
      userMap.put(sessionId, new User(username, userType));
    }

    // ユーザーを削除するメソッド
    public static void removeUser(String sessionId) {
      userMap.remove(sessionId);
    }

    // セッションIDからユーザーを取得するメソッド
    public static User getUser(String sessionId) {
      return userMap.get(sessionId);
    }
}
