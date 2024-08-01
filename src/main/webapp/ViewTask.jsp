<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.dao.TaskDAO,models.Task,java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Task List</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<style><%@include file="CSS/ViewTask.css"%></style>

<link rel="stylesheet" href="Viewtask.css">
</head>
<body>
	<%	
		TaskDAO task = new TaskDAO();
		List<Task> tasks = task.getTaskDetails();
	%>

    <div class="main">
        <div class="title"><h1>Task Details</h1></div><br>
        <table class="table table-dark table-hover">

            <thead>
                <th> Task ID </th>
                <th> User ID</th>
                <th> Project Name </th>
                <th> Task Date </th>
                <th> Start Time </th>
                <th> End Time </th>
                <th> Duration(Hours)</th>
                <th> Category</th>
                <th> Description </th>
                <th> Actions </th>
            </thead>
            
	            	<%
	                for(Task task_:tasks){
	            %>
	                <tr>
	                    <td><%= task_.getTaskId() %></td>
	                    <td><%= task_.getUserId() %></td>
	                    <td><%= task_.getProject() %></td>
	                    <td><%= task_.getTaskDate() %></td>
	                    <td><%= task_.getStartTime() %></td>
	                    <td><%= task_.getEndTime() %></td>
	                    <td><%= task_.getDuration() %></td>
	                    <td><%= task_.getCategory() %></td>
	                    <td><%= task_.getDescription() %></td>
	                    <td>
		                <form action="TaskDetailsServlet" method="post">
		                    <input type="hidden" name="task_id" value="<%= task_.getTaskId() %>" />
		                    <button type="submit">Edit/Delete</button>
	                	</form>
            			</td>
	                </tr>
	            <%} 
	            %>
            </table>
    </div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
