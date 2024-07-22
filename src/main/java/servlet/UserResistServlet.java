package servlet;

import main.User.UserType;
import main.User;
import main.UserManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/userResist")
public class UserResistServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Jspのフォーム画面からユーザ情報を取得
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		User user = new User(username, UserType.GUEST);
		// セッションスコープに登録ユーザを保存
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		String sessionId = session.getId();
		UserManager.addUser(sessionId, user);
		/// 次のページにフォワード
		String view = "WEB-INF/home.jsp"; // JSP を相対パスで指定
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println("move to home.jsp");
    dispatcher.forward(request, response);
	}
}