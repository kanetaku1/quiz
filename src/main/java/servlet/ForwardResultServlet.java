package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardResultServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /// 次のページにフォワード
        String view = "WEB-INF/result.jsp"; // JSP を相対パスで指定
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        System.out.println("move to result.jsp");
        dispatcher.forward(request, response); 
    }
}
