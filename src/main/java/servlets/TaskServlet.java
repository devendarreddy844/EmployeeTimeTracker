package servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.TaskDAO;

import models.Task;
import models.Users;


@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			response.sendRedirect("Login.jsp");
			return ;
		}
		Users user = (Users) session.getAttribute("Admin");
		
		
		if (user == null) {
			response.sendRedirect("Login.jsp");
			return ;
		}
		
//		String action = request.getParameter("action");
		TaskDAO task_dao = new TaskDAO();
		
//		if("add".equals(action)) {
			Task task = new Task();
			
			task.setUserId(Integer.parseInt(request.getParameter("user_id")));
            task.setProject(request.getParameter("project"));
            task.setTaskDate(Date.valueOf(request.getParameter("task_date")));
            
            String startTimeStr = request.getParameter("start_time") + ":00";
            String endTimeStr = request.getParameter("end_time") + ":00";
            
            try {
            	task.setStartTime(startTimeStr);
                task.setEndTime(endTimeStr);
            } catch(IllegalArgumentException e) {
            	e.printStackTrace();
            }
            
            task.setDuration(Integer.parseInt(request.getParameter("duration_time")));
            task.setCategory(request.getParameter("category"));
            task.setDescription(request.getParameter("description"));
            
            try {
				if (task_dao.checkTimeOverlap(task.getUserId(), task.getTaskDate(), task.getStartTime(), task.getEndTime())) {
				    request.setAttribute("errorMessage", "Task time overlaps with an existing task.");
				    request.getRequestDispatcher("AddTask.jsp").forward(request, response);
				    return;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            try {
				if (task_dao.addtask(task)) {
				    response.sendRedirect("AdminDashboard.jsp");
				} else {
				    request.setAttribute("errorMessage", "Failed to add task.");
				    request.getRequestDispatcher("AddTask.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		}
	}
	
	

}
