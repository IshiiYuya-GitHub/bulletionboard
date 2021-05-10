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
 * Servlet implementation class Posting
 */
public class Posting extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Posting() {
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

		String DB_URL = "jdbc:mariadb://localhost/bulletinboarddb";
		String DB_USER = "ishii";
		String DB_PASS = "memi0513";
		String mainPostingName = request.getParameter("mainPostingName");
		String mainPostingText = request.getParameter("mainPostingText");
		String mainPostingPass = request.getParameter("mainPostingPass");
		String replyPostingName = request.getParameter("replyPostingName");
		String replyPostingText = request.getParameter("replyPostingText");
		String replyPostingPass = request.getParameter("replyPostingPass");
		String stringReplyId = request.getParameter("replyId");
		int id = 0;
		int check = 0;
		int replyId = 0;
		int resultReplyId = 0;
		if (stringReplyId != null) {
			replyId = Integer.parseInt(stringReplyId);
		}

		if (mainPostingName != null && mainPostingText != null && mainPostingPass != null) {
			try (Connection conn1 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
				String sql1 = "INSERT INTO bulletinboard(name, text, password) VALUES(?, ?, ?)";
				PreparedStatement pStmt1 = conn1.prepareStatement(sql1);
				pStmt1.setString(1, mainPostingName);
				pStmt1.setString(2, mainPostingText);
				pStmt1.setString(3, mainPostingPass);
				pStmt1.executeUpdate();
				check++;
				try (Connection conn = DriverManager.getConnection(DB_URL , DB_USER, DB_PASS)) {
					String sql = "SELECT MAX(id) FROM bulletinboard";
					PreparedStatement pStmt = conn.prepareStatement(sql);
					ResultSet rs = pStmt.executeQuery();
					while (rs.next()) {
						id = rs.getInt("MAX(id)");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (replyPostingName != null && replyPostingText != null && replyPostingPass != null && replyId != 0) {
			try (Connection conn3 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
				String sql3 = "INSERT INTO reply(name, text, password, replyid) VALUES(?, ?, ?, ?)";
				PreparedStatement pStmt3 = conn3.prepareStatement(sql3);
				pStmt3.setString(1, replyPostingName);
				pStmt3.setString(2, replyPostingText);
				pStmt3.setString(3, replyPostingPass);
				pStmt3.setInt(4, replyId);
				pStmt3.executeUpdate();
				check += 2;
				try (Connection conn = DriverManager.getConnection(DB_URL , DB_USER, DB_PASS)) {
					String sql = "SELECT * FROM reply WHERE id = (SELECT MAX(id) FROM reply);";
					PreparedStatement pStmt = conn.prepareStatement(sql);
					ResultSet rs = pStmt.executeQuery();
					while (rs.next()) {
						id = rs.getInt("id");
						resultReplyId = rs.getInt("replyId");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String json = "";


		if (check == 1) {
			ReJackson MPjson = new ReJackson(true, id, mainPostingName, mainPostingText);
			json = mapper.writeValueAsString(MPjson);
		} else if (check == 2 && replyId == resultReplyId) {
			ReJackson RPjson = new ReJackson(true, id, replyPostingName, replyPostingText, replyId);
			json = mapper.writeValueAsString(RPjson);
		} else if (check == 0) {
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
