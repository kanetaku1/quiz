package servlet;

import main.GetGenre;
import main.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/host")
public class HostServlet extends HttpServlet{
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    GetGenre getGenre = new GetGenre();
    // APIからデータを取得
    List<String> genreList = getGenre.getDataFromAPI();
    // 取得したデータをリクエスト属性に設定
    request.setAttribute("genreList", genreList);
    // ユーザータイプをHOSTに設定
    User user = (User) request.getSession().getAttribute("user");
    user.setUserType(User.UserType.HOST);
    /// 次のページにフォワード
    String view = "WEB-INF/quiz.jsp"; 
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println(user.getUsername() + ": " + user.getUserType() + " move to quiz.jsp");
    dispatcher.forward(request, response); 
  }
}