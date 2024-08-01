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


@WebServlet("/ChartsAdmin")
public class ChartsAdmin extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		Users user = (Users) session.getAttribute("Admin");
		
		if (user == null) {
			response.sendRedirect("Login.jsp");
			return;
		}
		TaskDAO taskdao=new TaskDAO();
		List<Task> Tasks = taskdao.getTasks();
		
		request.setAttribute("tasks", Tasks);
		request.getRequestDispatcher("ChartsAdmin.jsp").forward(request, response);

	}
}
	
