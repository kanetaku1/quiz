package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hostServlet")
public class HostServlet extends HttpServlet{
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String view = "WEB-INF/host.jsp"; // JSP を相対パスで指定
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println("move to host.jsp");
    dispatcher.forward(request, response); 
  }
}