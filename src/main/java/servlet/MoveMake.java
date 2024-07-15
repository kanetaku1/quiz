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

@WebServlet("/forwardToMake")
public class MoveMake extends HttpServlet{
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    GetGenre getGenre = new GetGenre();
    // APIからデータを取得
    List<String> genreList = getGenre.getDataFromAPI();
    System.out.println("genreList: " + genreList);

    request.setAttribute("genreList", genreList);
    String view = "WEB-INF/make.jsp"; 
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println("move to make.jsp");
    dispatcher.forward(request, response); 
  }
}
