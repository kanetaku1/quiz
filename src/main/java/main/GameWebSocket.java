package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

@ServerEndpoint("/gameWebSocket")
public class GameWebSocket {

  GetQuiz getQuiz = new GetQuiz();
  private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

  private int currentQuestionIndex = -1;
  // private List<String> imagePaths = new ArrayList<>();
  // private List<String> questions = new ArrayList<>();
  // private List<String> answers = new ArrayList<>();

  @OnMessage
  public void onMessage(String message, Session session) throws IOException {

    // 受信したメッセージの解析
    try {
      // 受信したメッセージをJSON形式としてパース
      JSONObject jsonMessage = new JSONObject(message);

      // メッセージから"type"フィールドの値を取得
      String messageType = jsonMessage.getString("type");

      // メッセージの種類に応じて処理を実行
      // stargGameを受け取った場合
      System.out.println("messageType: " + messageType);
      if (messageType.equals("startGame")) {
        // ゲームを開始するための処理
        String genre = jsonMessage.getString("genre");
        // ここでジャンルを使って何かを行う
        getQuiz.getQuizData(genre);

        // System.out.println("Image Path: " + getQuiz.imagePaths);
        // System.out.println("Question: " + getQuiz.questions);
        // System.out.println("Answer: " + getQuiz.answers);
        // this.imagePaths = getQuiz.imagePaths;
        // this.questions = getQuiz.questions;
        // this.answers = getQuiz.answers;
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
  public void onOpen(Session session) {
      // クライアントが接続したときにログに追加
      sessions.add(session);
      sendMessageToAll(session.getId() + " joined the chat");
  }

  @OnClose
  public void onClose(Session session) {
      // クライアントが切断されたときにログに追加
      sessions.remove(session);
      sendMessageToAll(session.getId() + " left the chat");
  }

  private void sendMessageToAll(String message) {
    synchronized(sessions) {
      // 全てのクライアントにメッセージを送信
      for(Session s : sessions) {
        try {
          s.getBasicRemote().sendText(message);
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
        String question = getQuiz.quizList.getQuestions().get(currentQuestionIndex);
        String imagePath = getQuiz.quizList.getImagePaths().get(currentQuestionIndex);
        sendMessageToAll("問題:" + question);
        sendMessageToAll("写真:" + imagePath);
    } else {
        sendMessageToAll("問題はこれで終了です。");
        // ここで必要な後処理を行う（例：最終結果を表示するなど）
    }
  }
}
