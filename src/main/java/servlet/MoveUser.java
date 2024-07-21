package servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/User")
public class MoveUser extends HttpServlet{
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 次のページにフォワード
		String view = "WEB-INF/user.jsp"; // JSP を相対パスで指定
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		System.out.println("move to user.jsp");
		dispatcher.forward(request, response); 
	}
}

