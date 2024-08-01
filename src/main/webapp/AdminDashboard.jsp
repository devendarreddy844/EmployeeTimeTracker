<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="models.Users,models.Task,java.util.*" %>
<!DOCTYPE html>
<html>

<head>
    <link href="https://fonts.googleapis.com/css?family=Rethink+Sans&display=swap" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Outfit&display=swap" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet" />
    <link rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style><%@ include file="CSS/AdminDashboard.css"%></style>
    
    <title>Employee Time Tracker</title>
</head>

<body>

<%
	Users user = (Users) session.getAttribute("Admin");
%>
    <div class="main">

        <div class="employee">
            <div class="title">
                <div class="empn">
                    <p class="namee">Welcome back, <b><%=user.getUsername() %></b></p>
                    <span class="empid">EmpId:<%= user.getUserId() %></span>
                </div>
                <form action="LogoutServlet"><button class="logout" type="submit">Logout</button></form>
            </div>
        </div>
        <div class="btn">
            <button class="view" onclick="location.href='ViewTask.jsp';"><span class="material-symbols-outlined">visibility</span>View Task</button>
            <button class="atsk" onclick="location.href='AddTask.jsp';"><span class="material-symbols-outlined">add</span>Add Task</button>
        </div>
        
        
        <div class="stats">
        	<h1>Employee Stats</h1>
        	
        	<form action="TaskListServletAdmin">
        		<input type="text" name="user_id" required>
        		<button type="submit" class="sub">Submit</button>
        	</form>
        </div>
        
        <div class="charts">
        	<h1>Employee overall stats</h1>
        	<form action="ChartsAdmin">
        		<button type="submit" class="sub">Get overall stats</button>
        	</form>
        </div>
    </div>
</body>