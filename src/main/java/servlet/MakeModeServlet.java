package servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import main.SendQuiz;

@WebServlet("/makeMode")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10 )
public class MakeModeServlet extends HttpServlet{
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    SendQuiz sendQuiz = new SendQuiz();

    request.setCharacterEncoding("utf-8");
    // リクエストから画像、問題、ジャンル、回答を取得
    Part filePart = request.getPart("imageFile");
    String question = request.getParameter("question");
    String answer = request.getParameter("answer");
    String genre = request.getParameter("genre");
    if (genre.equals("new")) {
      genre = request.getParameter("newGenre");
    }

    // 画像のアップロード処理
    String imagePath = "";
    if (filePart.getSize() != 0l) {
      String fileName = filePart.getSubmittedFileName();
      String uploadsDir = getServletContext().getRealPath("/uploads");
      File uploadsDirFile = new File(uploadsDir);
      if (!uploadsDirFile.exists()) {
        uploadsDirFile.mkdir();
      }
      String filePath = uploadsDir + File.separator + fileName;
      filePart.write(filePath);
      imagePath = "uploads/" + fileName;
    }

    System.out.println("imagePath: " + imagePath);
    System.out.println("genre: " + genre);
    System.out.println("question: " + question);
    System.out.println("answer: " + answer);

    sendQuiz.SendData(imagePath, genre, question, answer);

    String view = "WEB-INF/home.jsp"; 
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println("move to home.jsp");
    dispatcher.forward(request, response); 
  }
}
