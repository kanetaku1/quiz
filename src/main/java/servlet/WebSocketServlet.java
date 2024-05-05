package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.DeploymentException;

@WebServlet("/startWebSocket")
public class WebSocketServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // WebSocketサーバーを起動
            main.WebSocketServer.startWebSocketServer();
            System.out.println("WebSocket server started...");
        } catch (DeploymentException e) {
            e.printStackTrace();
        }

        // 次のページにリダイレクト
        response.sendRedirect("roomSelection.jsp");
    }
}
