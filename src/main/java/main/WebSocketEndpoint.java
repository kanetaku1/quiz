package main;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class WebSocketEndpoint {
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        synchronized(sessions) {
            // 全てのクライアントにメッセージを送信
            for(Session s : sessions) {
                s.getBasicRemote().sendText(message);
            }
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
}