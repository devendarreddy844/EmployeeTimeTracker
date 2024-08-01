package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DAO;

/**
 * Servlet implementation class DeleteTaskServlet
 */
@WebServlet("/DeleteTaskServlet")
public class DeleteTaskServlet extends HttpServlet {
	private static final String DELETE_TASK_SQL = "DELETE FROM tasks where task_id = ?";
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int task_id = Integer.parseInt(request.getParameter("task_id"));
		
		try(Connection connection = DAO.getConnection();
				PreparedStatement TaskDeleteStmt = connection.prepareStatement(DELETE_TASK_SQL)){
			
			TaskDeleteStmt.setInt(1, task_id);
			
			int deleted =  TaskDeleteStmt.executeUpdate();
			
			if(deleted > 0) {
				response.getWriter().println("Task Deleted Successfully");
			}
			else {
				response.getWriter().println("Task Deletion Failed");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
