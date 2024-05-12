package main;

public class User {
  private String username;
  private UserType userType; // ホストかゲストかを示す列挙型

  public User(String username, UserType userType) {
    this.username = username;
    this.userType = userType;
  }

  // ゲッターとセッターなど、必要なメソッドを追加する

  public String getUsername() {
    return username;
  }

  public UserType getUserType() {
    return userType;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  public enum UserType {
    HOST,
    GUEST
  }
}
