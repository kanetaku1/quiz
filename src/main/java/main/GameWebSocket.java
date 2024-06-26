package main;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import org.json.JSONException;
import org.json.JSONObject;

@ServerEndpoint(value = "/gameWebSocket", configurator = GameWebSocket.Configurator.class)
public class GameWebSocket {

  GetQuiz getQuiz = new GetQuiz();
  // private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
  private static Map<Session, HttpSession> httpSessionMap = new ConcurrentHashMap<>();

  private int currentQuestionIndex = -1;
  // private List<String> imagePaths = new ArrayList<>();
  // private List<String> questions = new ArrayList<>();
  // private List<String> answers = new ArrayList<>();

  public static class Configurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
      HttpSession httpSession = (HttpSession) request.getHttpSession();
      config.getUserProperties().put(HttpSession.class.getName(), httpSession);
    }
  }

  @OnMessage
  public void onMessage(String message, Session session) throws IOException {
    // 受信したメッセージの解析
    try {
      // 受信したメッセージをJSON形式としてパース
      JSONObject jsonMessage = new JSONObject(message);
      // メッセージから"type"フィールドの値を取得
      String messageType = jsonMessage.getString("type");
      // メッセージの種類に応じて処理を実行
      System.out.println("messageType: " + messageType);
      if (messageType.equals("startGame")) {
        // ゲームを開始するための処理
        String genre = jsonMessage.getString("genre");
        getQuiz.getQuizData(genre);
      } else if (messageType.equals("nextQuiz")){
        sendNextQuestion(session);
        // 他のメッセージの種類に対する処理
      } else {

      }
    } catch (JSONException e) {
      // JSON形式エラーの処理
      e.printStackTrace();
    }
  }

  @OnOpen
  public void onOpen(Session session,  EndpointConfig config) {
    HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    httpSessionMap.put(session, httpSession);
    // クライアントが接続したときにログに追加
    User user = (User) httpSession.getAttribute("user");
    if (user != null) UserManager.addUser(session, user);
    String message = "joined this room";
    sendMessageToAll(session, message, user);
  }

  @OnClose
  public void onClose(Session session) {
    // クライアントが切断されたときにログに追加
    User user = UserManager.getUser(session);
    String message = "left this room";
    sendMessageToAll(session, message, user);
    UserManager.removeUser(session);
  }

  @OnError
  public void onError(Session session, Throwable throwable) {
      httpSessionMap.remove(session);
      UserManager.removeUser(session);
      throwable.printStackTrace();
  }

  private void sendMessageToAll(Session session, String message, User  user) {
    String username = "";
    if (user != null) {
      username = user.getUsername() + ": ";
    }  
    // メッセージをすべてのセッションにブロードキャスト
    for (Session s : session.getOpenSessions()) {
      if (s.isOpen()) {
        try {
          s.getBasicRemote().sendText(username + message);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void sendNextQuestion(Session session) throws IOException {
    currentQuestionIndex++;
    // System.out.println("next Question id: " + currentQuestionIndex + ", question size: " + this.questions.size() + " questions: " + this.questions + " imagePaths: " + this.imagePaths);
    if (currentQuestionIndex < getQuiz.quizList.getQuestions().size()) {
      String question = "問題:" + getQuiz.quizList.getQuestions().get(currentQuestionIndex);
      String imagePath = "写真:" + getQuiz.quizList.getImagePaths().get(currentQuestionIndex);
      sendMessageToAll(session, question, null);
      sendMessageToAll(session, imagePath, null);
    } else {
      String finish = "問題はこれで終了です";
        sendMessageToAll(session, finish, null);
        // ここで必要な後処理を行う（例：最終結果を表示するなど）
    }
  }
}
