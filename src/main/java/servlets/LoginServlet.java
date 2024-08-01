package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.DAO;
import models.Users;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    DAO dao = new DAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String Username = request.getParameter("username");
        String Password = request.getParameter("password");

        Users validate = dao.validate(Username, Password);

        try {
            if (validate != null) {
                if ("Admin".equals(validate.getRole())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("Admin", validate);
                    request.setAttribute("Username", Username);
                    request.getRequestDispatcher("AdminDashboard.jsp").forward(request, response);
                } else if ("Associate".equals(validate.getRole())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("Associate", validate);
                    request.setAttribute("Username", Username);
                    response.sendRedirect("AssociateDashboard.jsp");
                }
            } else {
                // Invalid username or password
                request.setAttribute("errorMessage", "Invalid username or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
