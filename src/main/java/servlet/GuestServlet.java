package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/guest")
public class GuestServlet extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String view = "WEB-INF/guest.jsp"; // JSP を相対パスで指定
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println("move to guest.jsp");
    dispatcher.forward(request, response); 
  }
}
