package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.GetGenre;

@WebServlet("/fowardToSearch")
public class ForwardSearchServlet extends HttpServlet{

  GetGenre getGenre = new GetGenre();

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // APIからデータを取得
    List<String> genreList = getGenre.getDataFromAPI();
    System.out.println("genreList: " + genreList);
    // 取得したデータをリクエスト属性に設定
    request.setAttribute("genreList", genreList);

    String view = "WEB-INF/search.jsp"; 
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println("move to search.jsp");
    dispatcher.forward(request, response); 
  }
}
