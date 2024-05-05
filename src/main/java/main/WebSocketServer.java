package main;
import org.glassfish.tyrus.server.Server;
import javax.websocket.DeploymentException;

public class WebSocketServer {
    public static void startWebSocketServer() throws DeploymentException {

        String HoriutiLab = "192.168.11.14";

        // WebSocketサーバーを起動
        Server server = new Server("localhost", 8025, "", null, WebSocketEndpoint.class);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
