package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class MoveHome extends HttpServlet{
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /// 次のページにフォワード
    String view = "WEB-INF/home.jsp"; 
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println("move to home.jsp");
    dispatcher.forward(request, response); 
  }
}