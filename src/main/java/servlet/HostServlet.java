package servlet;
import main.GetGenre;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import main.User;

@WebServlet("/host")
public class HostServlet extends HttpServlet{

  GetGenre getGenre = new GetGenre();

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // APIからデータを取得
    List<String> genreList = getGenre.getDataFromAPI();
    System.out.println("genreList: " + genreList);
    // 取得したデータをリクエスト属性に設定
    request.setAttribute("genreList", genreList);

    User user = (User) request.getSession().getAttribute("user");
    user.setUserType(User.UserType.HOST);

    String view = "WEB-INF/quiz.jsp"; 
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println(user.getUsername() + ": " + user.getUserType() + " move to quiz.jsp");
    dispatcher.forward(request, response); 
  }
}