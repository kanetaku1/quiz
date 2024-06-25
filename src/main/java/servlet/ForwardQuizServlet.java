package servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/forwardToQuiz")
public class ForwardQuizServlet extends HttpServlet {
    public String sessionId = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 次のページにフォワード
        String view = "WEB-INF/roomSelection.jsp"; // JSP を相対パスで指定
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        System.out.println("move to roomSelection.jsp");
        dispatcher.forward(request, response); 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HttpSession session = request.getSession();
      sessionId = session.getId();
    }
}
