package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/forwardToGame")
public class ForwardGameServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userType = request.getParameter("userType");
    request.setAttribute("userType", userType);

    if(userType.equals("HOST")){
      String genre = request.getParameter("genre");
      request.setAttribute("selectedGenre", genre);
    }

    String view = "WEB-INF/game.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println(userType + " move to game.jsp");
    dispatcher.forward(request, response);
  }
}
