package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.TaskDAO;

import models.Task;
import models.Users;


@WebServlet("/TaskListServletAdmin")
public class TaskListServletAdmin extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		
		HttpSession session = request.getSession(false);
		Users user = (Users) session.getAttribute("Admin");
		
		if (user == null) {
			response.sendRedirect("Login.jsp");
			return;
		}
		
		
		
		TaskDAO taskdao=new TaskDAO();
		
		List<Task> dailyTasks = taskdao.getDailyTasks(user_id);
		
		List<Task> weeklyTasks = taskdao.getWeeklyTasks(user_id);
		
		List<Task> monthlyTasks = taskdao.getMonthlyTasks(user_id);
		

		request.setAttribute("dailyTasks",dailyTasks);
		request.setAttribute("weeklyTasks", weeklyTasks);
		request.setAttribute("monthlyTasks", monthlyTasks);
		request.getRequestDispatcher("Charts.jsp").forward(request, response);

	}
}
	













