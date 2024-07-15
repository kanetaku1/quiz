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
import main.QuizList;

@WebServlet("/searchMode")
public class SearchModeServlet extends HttpServlet {  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // ジャンルを取得
    String selectedGenre = request.getParameter("selectedGenre");

    if (selectedGenre != ""){
      GetQuiz getQuiz = new GetQuiz();
      getQuiz.getQuizData(selectedGenre);
      // getQuiz.quizList.showData();
      QuizList quizList = getQuiz.quizList;
      request.setAttribute("quizList", quizList);
      request.setAttribute("selectedGenre", selectedGenre);
    }

    String view = "WEB-INF/search.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println("move to search.jsp");
    dispatcher.forward(request, response);
  }

}
