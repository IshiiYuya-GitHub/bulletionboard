package connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import memory.Reply;
import memory.Timeline;

/**
 * Servlet implementation class ConnectDB
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("UTF-8");

		String DB_URL = "jdbc:mariadb://localhost/bulletinboarddb";
		String DB_USER = "ishii";
		String DB_PASS = "memi0513";

		ArrayList<Timeline> timeline = new ArrayList<>();
		ArrayList<Reply> reply = new ArrayList<>();


		// コメントの取得
		try(Connection conn2 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
			String sql2 = "SELECT id, name, text, is_deleted FROM bulletinboard ORDER BY id ASC";
			String sql3 = "SELECT replyid, id, name, text, is_deleted FROM reply ORDER BY replyid ASC";
			PreparedStatement pStmt2 = conn2.prepareStatement(sql2);
			PreparedStatement pStmt3 = conn2.prepareStatement(sql3);

			ResultSet rs2 = pStmt2.executeQuery();
			ResultSet rs3 = pStmt3.executeQuery();

			while(rs2.next()) {
				int getId = rs2.getInt("id");
				String getName = rs2.getString("name");
				String getText = rs2.getString("text");
				boolean getIsDeleted = rs2.getBoolean("is_deleted");
				Timeline tl = new Timeline(getId, getName, getText, getIsDeleted);
				timeline.add(tl);
			}
			request.setAttribute("timeline", timeline);

			while(rs3.next()) {
				int getReplyId = rs3.getInt("replyid");
				int getId = rs3.getInt("id");
				String getName = rs3.getString("name");
				String getText = rs3.getString("text");
				boolean getIsDeleted = rs3.getBoolean("is_deleted");
				Reply rp = new Reply(getReplyId, getId, getName, getText, getIsDeleted);
				reply.add(rp);
			}
			request.setAttribute("reply", reply);


			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/bulletinboard.jsp");
			dispatcher.forward(request, response);




		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
