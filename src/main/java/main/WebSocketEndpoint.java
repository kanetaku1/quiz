package main;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket", configurator = WebSocketEndpoint.Configurator.class)
public class WebSocketEndpoint {
    private static Map<Session, HttpSession> httpSessionMap = new ConcurrentHashMap<>();

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    public static class Configurator extends ServerEndpointConfig.Configurator {
        @Override
        public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
            HttpSession httpSession = (HttpSession) request.getHttpSession();
            config.getUserProperties().put(HttpSession.class.getName(), httpSession);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        User user = UserManager.getUser(session);
        if (user != null) {
            String username = user.getUsername();
            // メッセージをすべてのセッションにブロードキャスト
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen()) {
                    try {
                        s.getBasicRemote().sendText(username + ": " + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        httpSessionMap.put(session, httpSession);

        // クライアントが接続したときにログに追加
        sessions.add(session);
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            UserManager.addUser(session, user);
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
    public void onClose(Session session) {
        // クライアントが切断されたときにログに追加
        User user = UserManager.getUser(session);
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
        UserManager.removeUser(session);
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        httpSessionMap.remove(session);
        UserManager.removeUser(session);
        throwable.printStackTrace();
    }
}