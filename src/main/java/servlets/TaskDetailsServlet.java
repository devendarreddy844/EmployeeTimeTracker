package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DAO;

/**
 * Servlet implementation class TaskDetailsServlet
 */
@WebServlet("/TaskDetailsServlet")
public class TaskDetailsServlet extends HttpServlet {
	
	private static final String SELECT_TASK_SQL = "SELECT * FROM tasks WHERE task_id=?";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int task_id = Integer.parseInt(request.getParameter("task_id"));
		
		
		try(Connection con = DAO.getConnection();
				PreparedStatement selectTaskStmt = con.prepareStatement(SELECT_TASK_SQL)){
			
			selectTaskStmt.setInt(1,task_id);
			
			ResultSet rs = selectTaskStmt.executeQuery();
			
			if(rs.next()) {
				request.setAttribute("task_id", rs.getInt("task_id"));
				request.setAttribute("user_id", rs.getInt("user_id"));
				request.setAttribute("project", rs.getString("project"));
				request.setAttribute("task_date", rs.getDate("task_date"));
				request.setAttribute("start_time", rs.getString("start_time"));
				request.setAttribute("end_time", rs.getString("end_time"));
				request.setAttribute("duration", rs.getInt("duration"));
				request.setAttribute("category", rs.getString("category"));
				request.setAttribute("description", rs.getString("description"));
				
				request.getRequestDispatcher("EditTaskDetails.jsp").forward(request, response);
				
			}
			else {
				response.getWriter().println("Task Not Found");
			}
		} catch (SQLException e) {
			
		}
		
	}

}
