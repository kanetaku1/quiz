package servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import main.SendQuiz;

@WebServlet("/makeMode")
public class MakeModeServlet extends HttpServlet{

  SendQuiz sendQuiz = new SendQuiz();

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // リクエストから画像、問題、ジャンル、回答を取得
    Part filePart = request.getPart("imageFile");
    String genre = request.getParameter("genre");
    String question = request.getParameter("question");
    String answer = request.getParameter("answer");

    // 画像のアップロード処理
    String imagePath = "";
    if (filePart != null) {
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
  }
}
