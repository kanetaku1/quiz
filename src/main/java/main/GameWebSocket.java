package main;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import org.json.JSONException;
import org.json.JSONObject;

// , configurator = GameWebSocket.Configurator.class
@ServerEndpoint(value = "/gameWebSocket/{sessionId}")
public class GameWebSocket {

  GetQuiz getQuiz = new GetQuiz();
  // private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
  // private static Map<Session, HttpSession> httpSessionMap = new ConcurrentHashMap<>();

  private Session session;
  private String sessionId;
  private User user;
  private int currentQuestionIndex = -1;
  // private List<String> imagePaths = new ArrayList<>();
  // private List<String> questions = new ArrayList<>();
  // private List<String> answers = new ArrayList<>();

  // public static class Configurator extends ServerEndpointConfig.Configurator {
  //   @Override
  //   public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
  //     HttpSession httpSession = (HttpSession) request.getHttpSession();
  //     config.getUserProperties().put(HttpSession.class.getName(), httpSession);
  //   }
  // }

  @OnMessage
  public void onMessage(String message) throws IOException {
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
        sendNextQuestion();
        // 他のメッセージの種類に対する処理
      } else {

      }
    } catch (JSONException e) {
      // JSON形式エラーの処理
      e.printStackTrace();
    }
  }

  @OnOpen
  public void onOpen(Session session, @PathParam("sessionId") String sessionId) {
    // HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    // httpSessionMap.put(session, httpSession);

    this.session = session;
    this.sessionId = sessionId;
    this.user = UserManager.getUser(sessionId);
    System.out.println(user);
    // クライアントが接続したときにログに追加
    // User user = (User) httpSession.getAttribute("user");
    // if (user != null) UserManager.addUser(sessionId, user);
    String message = "joined this room";
    sendMessageToAll(message, user);
  }

  @OnClose
  public void onClose() {
    // クライアントが切断されたときにログに追加
    // User user = UserManager.getUser(sessionId);
    String message = "left this room";
    sendMessageToAll(message, user);
  }

  @OnError
  public void onError(Throwable throwable) {
      // httpSessionMap.remove(sessionId);
      UserManager.removeUser(sessionId);
      throwable.printStackTrace();
  }

  private void sendMessageToAll(String message, User  name) {
    String username = "";
    if (name != null) {
      username = name.getUsername() + ": ";
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

  private void sendNextQuestion() throws IOException {
    currentQuestionIndex++;
    // System.out.println("next Question id: " + currentQuestionIndex + ", question size: " + this.questions.size() + " questions: " + this.questions + " imagePaths: " + this.imagePaths);
    if (currentQuestionIndex < getQuiz.quizList.getQuestions().size()) {
      String question = "問題:" + getQuiz.quizList.getQuestions().get(currentQuestionIndex);
      String imagePath = "写真:" + getQuiz.quizList.getImagePaths().get(currentQuestionIndex);
      sendMessageToAll(question, null);
      sendMessageToAll(imagePath, null);
    } else {
      String finish = "問題はこれで終了です";
        sendMessageToAll(finish, null);
        // ここで必要な後処理を行う（例：最終結果を表示するなど）
    }
  }
}
