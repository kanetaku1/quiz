package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.GetQuiz;

@WebServlet("/searchMode")
public class SearchModeServlet extends HttpServlet {

  GetQuiz getQuiz = new GetQuiz();
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // ジャンルを取得
    String genre = request.getParameter("genre");

    // ここでジャンルを使って何かを行う
    getQuiz.getQuizData(genre);
    getQuiz.quizList.showData();

    // ジャンルリストを取得&セット
    List<String> genreList = (List<String>) request.getSession().getAttribute("genres");
    request.setAttribute("genreList", genreList);

    request.setAttribute("imagePath", getQuiz.quizList.getImagePaths());
    request.setAttribute("question", getQuiz.quizList.getQuestions());
    request.setAttribute("answer", getQuiz.quizList.getAnswers());
    request.setAttribute("selectedGenre", genre);

    String view = "WEB-INF/search.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println("move to search.jsp");
    dispatcher.forward(request, response);
  }

}
