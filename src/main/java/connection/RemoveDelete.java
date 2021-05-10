package connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemoveDelete
 */
public class RemoveDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		int resultCheck = 0;
		String checkPass = "";
		String mainRemoveDeletePass = request.getParameter("mainRemoveDeletePass");
		String mainRemoveDeleteKind = request.getParameter("mainRemoveDeleteKind");
		String mrdid = request.getParameter("mainRemoveDeleteId");
		int mainRemoveDeleteId = 0;
		String replyRemoveDeletePpass = request.getParameter("replyRemoveDeletePass");
		String replyRemoveDeleteKind = request.getParameter("replyRemoveDeleteKind");
		String rrdid = request.getParameter("replyRemoveDeleteId");
		int replyRemoveDeleteId = 0;
		int check = 0;

		// 削除のメイン、リプライで分ける
		if (mrdid != null) {
			mainRemoveDeleteId = Integer.parseInt(mrdid);
			check++;
			replyRemoveDeleteKind = "";
			replyRemoveDeletePpass = "";
		} else if (rrdid != null) {
			replyRemoveDeleteId = Integer.parseInt(rrdid);
			check += 2;
			mainRemoveDeleteKind = "";
			mainRemoveDeletePass = "";
		}

		String DB_URL = "jdbc:mariadb://localhost/bulletinboarddb";
		String DB_USER = "ishii";
		String DB_PASS = "memi0513";

		if (check == 1) {
			try (Connection conn1 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
				String sql1 = "SELECT password FROM bulletinboard WHERE id = ?";
				PreparedStatement pStmt1 = conn1.prepareStatement(sql1);
				pStmt1.setInt(1, mainRemoveDeleteId);
				ResultSet rs1 = pStmt1.executeQuery();
				while (rs1.next()) {
					checkPass = rs1.getString("password");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (check == 2) {
			try (Connection conn2 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
				String sql2 = "SELECT password FROM reply WHERE id = ?";
				PreparedStatement pStmt2 = conn2.prepareStatement(sql2);
				pStmt2.setInt(1, replyRemoveDeleteId);
				ResultSet rs2 = pStmt2.executeQuery();
				while (rs2.next()) {
					checkPass = rs2.getString("password");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (mainRemoveDeleteKind.equals("delete") && check == 1 && mainRemoveDeletePass.equals(checkPass)) {
			try (Connection conn1 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
				String sql1 = "DELETE FROM bulletinboard WHERE id = ?";
				PreparedStatement pStmt1 = conn1.prepareStatement(sql1);
				pStmt1.setInt(1, mainRemoveDeleteId);
				pStmt1.executeUpdate();
				resultCheck++;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (replyRemoveDeleteKind.equals("delete") && check == 2 && replyRemoveDeletePpass.equals(checkPass)) {
			try (Connection conn2 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
				String sql2 = "DELETE FROM reply WHERE id = ?";
				PreparedStatement pStmt2 = conn2.prepareStatement(sql2);
				pStmt2.setInt(1, replyRemoveDeleteId);
				pStmt2.executeUpdate();
				resultCheck++;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (mainRemoveDeleteKind.equals("remove") && check == 1 && mainRemoveDeletePass.equals(checkPass)) {
			try (Connection conn3 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
				String sql3 = "UPDATE bulletinboard SET `is_deleted` = 1 WHERE id = ?";
				PreparedStatement pStmt3 = conn3.prepareStatement(sql3);
				pStmt3.setInt(1, mainRemoveDeleteId);
				pStmt3.executeUpdate();
				resultCheck+=2;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (replyRemoveDeleteKind.equals("remove") && check == 2 && replyRemoveDeletePpass.equals(checkPass)) {
			try (Connection conn4 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
				String sql4 = "UPDATE reply SET `is_deleted` = 1 WHERE id = ?";
				PreparedStatement pStmt4 = conn4.prepareStatement(sql4);
				pStmt4.setInt(1, replyRemoveDeleteId);
				pStmt4.executeUpdate();
				resultCheck+=2;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		PrintWriter out = response.getWriter();
		out.print(resultCheck);
	}
}
