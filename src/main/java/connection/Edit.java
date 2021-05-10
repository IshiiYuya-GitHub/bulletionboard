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

import com.fasterxml.jackson.databind.ObjectMapper;

import memory.ReJackson;

/**
 * Servlet implementation class Edit
 */
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("UTF-8");

		String meid = request.getParameter("mainEditId");
		String checkPass = "";
		int mainEditId = 0;
		String mainEditText = request.getParameter("mainEditText");
		String mainEditPass = request.getParameter("mainEditPass");
		String reid = request.getParameter("replyEditId");
		int replyEditId = 0;
		String replyEditText = request.getParameter("replyEditText");
		String replyEditPass = request.getParameter("replyEditPass");
		int check = 0;
		int resultCheck = 0;

		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String json = "";

		if (meid != null) {
			mainEditId = Integer.parseInt(meid);
			check++;
			replyEditPass = "";
		} else if (reid != null) {
			replyEditId = Integer.parseInt(reid);
			check += 2;
			mainEditPass = "";
		}

		String DB_URL = "jdbc:mariadb://localhost/bulletinboarddb";
		String DB_USER = "ishii";
		String DB_PASS = "memi0513";

		if (check == 1) {
			try (Connection conn1 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
				String sql1 = "SELECT password FROM bulletinboard WHERE id = ?";
				PreparedStatement pStmt1 = conn1.prepareStatement(sql1);
				pStmt1.setInt(1, mainEditId);
				ResultSet rs1 = pStmt1.executeQuery();
				while (rs1.next()) {
					checkPass = rs1.getString("password");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				json = "{\"check\":false}";
				out.print(json);
			}
		} else if (check == 2) {
			try (Connection conn2 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
				String sql2 = "SELECT password FROM reply WHERE id = ?";
				PreparedStatement pStmt2 = conn2.prepareStatement(sql2);
				pStmt2.setInt(1, replyEditId);
				ResultSet rs2 = pStmt2.executeQuery();
				while (rs2.next()) {
					checkPass = rs2.getString("password");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				json = "{\"check\":false}";
				out.print(json);
			}
		}
		if (mainEditPass.equals(checkPass)) {
			try (Connection conn1 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
				String sql1 = "UPDATE bulletinboard SET text = ? WHERE id = ?";
				PreparedStatement pStmt1 = conn1.prepareStatement(sql1);
				pStmt1.setString(1, mainEditText);
				pStmt1.setInt(2, mainEditId);
				resultCheck = pStmt1.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				json = "{\"check\":false}";
				out.print(json);
			}
		} else if (replyEditPass.equals(checkPass)) {
			try (Connection conn2 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
				String sql2 = "UPDATE reply SET text = ? WHERE id = ?";
				PreparedStatement pStmt2 = conn2.prepareStatement(sql2);
				pStmt2.setString(1, replyEditText);
				pStmt2.setInt(2, replyEditId);
				resultCheck = pStmt2.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				json = "{\"check\":false}";
				out.print(json);
			}
		}
		if (resultCheck != 0 && check ==1) {
			ReJackson MEjson = new ReJackson(true, mainEditText);
			json = mapper.writeValueAsString(MEjson);
		} else if (resultCheck != 0 && check == 2) {
			ReJackson REjson = new ReJackson(true, replyEditText);
			json = mapper.writeValueAsString(REjson);
		} else {
			json = "{\"check\":false}";
		}

		out.print(json);
		out.close();


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
