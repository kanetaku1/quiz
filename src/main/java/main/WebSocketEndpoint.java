package main;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// , configurator = WebSocketEndpoint.Configurator.class
@ServerEndpoint("/websocket/{sessionId}")
public class WebSocketEndpoint {
    private Session session;
    private String sessionId;
    private User user;

    // private static Map<Session, HttpSession> httpSessionMap = new ConcurrentHashMap<>();

    // private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    // public static class Configurator extends ServerEndpointConfig.Configurator {
    //     @Override
    //     public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
    //         HttpSession httpSession = (HttpSession) request.getHttpSession();
    //         config.getUserProperties().put(HttpSession.class.getName(), httpSession);
    //     }
    // }

    @OnMessage
    public void onMessage(String message) throws IOException {
        // User user = UserManager.getUser(sessionId);
        if (user != null) {
            String username = user.getUsername();
            // メッセージをすべてのセッションにブロードキャスト
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen()) {
                    try {
                        if(message.equals("host: push startButton")){
                            s.getBasicRemote().sendText(message);
                        } else{
                            s.getBasicRemote().sendText(username + ": " + message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // , EndpointConfig config
    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") String sessionId) {
        this.session = session;
        this.sessionId = sessionId;
        this.user = UserManager.getUser(sessionId);

        System.out.println(user);
        // HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        // httpSessionMap.put(session, httpSession);
        // // クライアントが接続したときにログに追加
        // User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            // UserManager.addUser(sessionId, user);
            for (Session s : session.getOpenSessions()) {
                try {
                    s.getBasicRemote().sendText(user.getUsername() + " joined this room");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClose
    public void onClose() {
        // クライアントが切断されたときにログに追加
        // User user = UserManager.getUser(sessionId);
        if (user != null) {
            String username = user.getUsername();
            // メッセージをすべてのセッションにブロードキャスト
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen()) {
                    try {
                        s.getBasicRemote().sendText(username + ": " + "left this room");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @OnError
    public void onError(Throwable throwable) {
        // httpSessionMap.remove(session);
        UserManager.removeUser(sessionId);
        throwable.printStackTrace();
    }
}