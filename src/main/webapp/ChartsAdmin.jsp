<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="models.Task,java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Charts Admin</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>


		<div class="charts">
        	<canvas id="TasksChart"></canvas>
        </div>


	<%
	   	List<Task> Tasks = (List<Task>) request.getAttribute("tasks"); 
	   %>
	   
	    <script>
	        const taskCategories = {};
	        <% for (Task task : Tasks) { %>
	            project = "<%= task.getProject() %>";
	            duration = <%= task.getDuration() %>;
	
	            if (!taskCategories[project]) {
	                taskCategories[project] = 0;
	            }
	            taskCategories[project] += duration;
	        <% } %>
	        const labels = Object.keys(taskCategories);
	        const data = Object.values(taskCategories);
	
			const ctx = document.getElementById('TasksChart').getContext('2d');
		        
		        const weeklyTasks = new Chart(ctx, {
		            type: 'bar',
		            data: {
		                labels: labels,
		                datasets: [{
		                    label: 'Task Duration (hours)',
		                    data: data,
		                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
		                    borderColor: 'rgba(255, 99, 132, 0.2)',
		                    borderWidth: 1
		                }]
		            },
		            options: {
		                scales: {
		                    y: {
		                        beginAtZero: true
		                    }
		                }
		            }
		        });
	     </script>
</body>
</html>