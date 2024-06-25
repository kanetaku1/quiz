package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import main.User;
import main.User.UserType;
import main.UserManager;

@WebServlet("/forwardToForm")
public class ForwardFormServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String view = "WEB-INF/form.jsp"; // JSP を相対パスで指定
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    System.out.println("move to form.jsp");
    dispatcher.forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Jspのフォーム画面からユーザ情報を取得
		request.setCharacterEncoding("UTF-8");
		String user_name = request.getParameter("Username");
		
		// ユーザ情報格納クラスをインスタンス
		UserManager user = new UserManager();
		
		// ユーザ情報を格納する
		
		// セッションスコープに登録ユーザを保存
		HttpSession session = request.getSession();
		
		// 画面へ遷移
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/userinfo_session.jsp");
		rd.forward(request, response);
		
	}
}
