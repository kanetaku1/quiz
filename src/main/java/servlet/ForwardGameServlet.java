package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.User;

@WebServlet("/forwardToGame")
public class ForwardGameServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
    
    if(user.getUserType() == User.UserType.valueOf("HOST")){
      String genre = request.getParameter("genre");
      request.setAttribute("selectedGenre", genre);
      System.out.println(user.getUsername() + " is HOST");
    }
    System.out.println(user.getUsername() + " is GUEST");

    String view = "WEB-INF/game.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println(user.getUserType() + " move to game.jsp");
    dispatcher.forward(request, response);
  }
}
