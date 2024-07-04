package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.User;
import java.io.IOException;

@WebServlet("/guest")
public class GuestServlet extends HttpServlet {
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    User user = (User) request.getSession().getAttribute("user");
    user.setUserType(User.UserType.GUEST);

    String view = "WEB-INF/quiz.jsp"; 
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println(user.getUsername() + ": " + user.getUserType() + " move to quiz.jsp");
    dispatcher.forward(request, response); 
  }
}
