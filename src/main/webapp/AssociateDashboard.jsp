<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="models.Users,com.dao.TaskDAO,models.Task,java.util.*" %>
<!DOCTYPE html>
<html>

<head>
    <link href="https://fonts.googleapis.com/css?family=Rethink+Sans&display=swap" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Outfit&display=swap" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet" />
    <style><%@ include file="CSS/AssociateDashboard.css" %></style>
    <title>Employee Time Tracker</title>
</head>

<body>
<%
	if(session == null || session.getAttribute("Associate") == null){
		response.sendRedirect("Login.jsp");
		return ;
	}
	Users user = (Users) session.getAttribute("Associate");
	int user_id = user.getUserId();
	TaskDAO task = new TaskDAO();
	Map<String,List<Task>> tasks = task.getTaskDetailsByAssociate(user_id);

%>
    <div class="main">
        <div class="employee">
            <div class="title">
                <div class="empn">
                    <p class="namee">Welcome back, <b><%= user.getUsername() %></b></p>
                    <span class="empid">EmpId:<%= user.getUserId() %></span>
                </div>
                <form action="LogoutServlet"><button class="logout" type="submit">Logout</button></form>
            </div>
        </div>
        
        <div class="stats">
        	<form action="TaskListServlet">
        		<button class="adt" type="submit"> Stats</button>
        	</form>
        </div>
        
        <%
        	for(Map.Entry<String,List<Task>> task_:tasks.entrySet()){
        		
        		String project = task_.getKey();
        		List<Task> task1 = task_.getValue();
        		
        %>
        <div class="project">
            <div class="desc">
                <div class="empn">
                    <p class="projn"><h2><%= project %></h2></p>
                    <span class="projdesc"></span>
                </div>
                <button class="adt" onclick="location.href='AddTask.jsp';">Add Task</button>
            </div>
        </div>
		

        <div class="tasko">
            <p>Tasks:</p>
        </div>
        		<%	
        		
        		for (int i= 0; i<task1.size();i++){
        			Task task2 = task1.get(i);
        		
        		%>

			        <div class="task-1">
			            <div class="taskso">
			                <p class="projn"><%= task2.getTaskId() %> </p>
			                <span class="projdesc"><%= task2.getTaskDate() %> <%= task2.getStartTime() %>-<%= task2.getEndTime() %></span>
			                <span><%= task2.getCategory() %><br>
			                   <%= task2.getDescription() %></span>
			            </div>
			            
			     <%}
        		%>
          <hr>  
         <% }
        %>
        </div>
        
        



    </div>
</body>
</html>