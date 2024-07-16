package main;

import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{sessionId}")
public class WebSocketEndpoint {
    private Session session;
    private String sessionId;
    private User user;
    private static QuizManager quizManager = new QuizManager();
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();
    
    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") String sessionId) {
        this.session = session;
        this.sessionId = sessionId;
        this.user = UserManager.getUser(sessionId);
        sessions.put(sessionId, session);
       
        if (user != null) {
            quizManager.addUser(user);
            broadcastUserList();
            sendMessage(session, createJsonMessage("chat", "Successfully connected to the quiz game."));
        } else {
            sendMessage(session, createJsonMessage("chat", "User not found."));
            try{
                session.close();
            } catch (IOException e){
                e.printStackTrace();
            } 
        }
     
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        JSONObject jsonMessage = new JSONObject(message);
        String action = jsonMessage.getString("action");
        switch (action) {
            case "chat":
                broadcastChatMessage(jsonMessage);
                break;
            case "submitAnswer":
                submitAnswer(jsonMessage);
                break;
            case "startGame":
                if(user.getUserType() == User.UserType.HOST) {
                    startGame(jsonMessage);
                }
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(sessionId);
        broadcastUserList();
        if (user != null) {
            quizManager.removeUser(user.getUsername());
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        sessions.remove(sessionId);
        UserManager.removeUser(sessionId);
        throwable.printStackTrace();
    }

    private void startGame(JSONObject message) {
        String genre = message.getString("genre");
        quizManager.prepareQuiz(genre);
        broadcastMessage(createJsonMessage("gameStarted", genre));
        sendNextQuestion();
    }

    private void submitAnswer(JSONObject message) {
        String answer = message.getString("answer");
        boolean isCorrect = quizManager.checkAnswer(user, answer);
        sendMessage(session, createJsonMessage("ServerMessage", isCorrect ? "Correct!" : "Incorrect!"));
        if (quizManager.allAnswered()) {
            if (quizManager.hasMoreQuestions()) {
                sendNextQuestion();
            } else {
                endGame();
            }
        }
    }

    private void sendNextQuestion() {
        String nextQuiz = quizManager.getNextQuestion();
        broadcastMessage(new JSONObject(nextQuiz)
            .put("type", "quiz")
            .toString());
    }

    private void endGame() {
        Map<User, Integer> scores = quizManager.getFinalScores();
        JSONObject scoreBuilder = new JSONObject();
        for (Map.Entry<User, Integer> entry : scores.entrySet()) {
            User user = entry.getKey();
            Integer score = entry.getValue();
            scoreBuilder.put(user.getUsername(), score);
        }
        broadcastMessage(new JSONObject()
            .put("type", "gameEnd")
            .put("scores", scoreBuilder)
            .toString());
    }

    private void broadcastUserList() {
        String userListJson = quizManager.getUserListJson();
        broadcastMessage(new JSONObject()
            .put("type", "userList")
            .put("data", userListJson)
            .toString());
    }

    private void broadcastChatMessage(JSONObject message) {
        String chatMessage = message.getString("message");
        broadcastMessage(createJsonMessage("chat", user.getUsername() + ": " + chatMessage));
    }

    private void broadcastMessage(String message) {
        for (Session session : sessions.values()) {
            sendMessage(session, message);
        }
    }

    private void sendMessage(Session session, String message){
        try{
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createJsonMessage(String type, String content){
        return new JSONObject()
            .put("type", type)
            .put("content", content)
            .toString();
    }
}