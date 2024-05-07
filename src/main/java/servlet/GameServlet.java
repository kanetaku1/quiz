package servlet;
import main.GetQuiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/gameStart")
public class GameServlet extends HttpServlet {

  GetQuiz getQuiz = new GetQuiz();

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // ジャンルを取得
    String genre = request.getParameter("genre");

    // ここでジャンルを使って何かを行う
    getQuiz.getQuizData(genre);

    System.out.println("Image Path: " + getQuiz.imagePaths);
    System.out.println("Question: " + getQuiz.questions);
    System.out.println("Answer: " + getQuiz.answers);

    request.setAttribute("imagePath", getQuiz.imagePaths);
    request.setAttribute("question", getQuiz.questions);
    request.setAttribute("answer", getQuiz.answers);

    String view = "WEB-INF/game.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println("move to game.jsp");
    dispatcher.forward(request, response);
  }

}
