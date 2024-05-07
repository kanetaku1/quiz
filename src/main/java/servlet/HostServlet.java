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

@WebServlet("/host")
public class HostServlet extends HttpServlet{

  GetGenre getGenre = new GetGenre();
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // APIからデータを取得
    List<String> genreList = getGenre.getDataFromAPI();
    System.out.println("genreList: " + genreList);
    // 取得したデータをリクエスト属性に設定
    request.setAttribute("genreList", genreList);

    String view = "WEB-INF/host.jsp"; 
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println("move to host.jsp");
    dispatcher.forward(request, response); 
  }
}