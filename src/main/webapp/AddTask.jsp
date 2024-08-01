<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <link href="https://fonts.googleapis.com/css?family=Rethink+Sans&display=swap" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Outfit&display=swap" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet" />
    <link rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
    <link href="./T2adtful.css" rel="stylesheet" />
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

    <title>Add Task</title>
    
    <style><%@ include file="CSS/AddTask.css"%></style>
</head>

<body>


    <div class="bgr">
    
        <div class="frm">
        
        <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
        
        <div class="alert alert-danger alert-dismissible" role="alert">
  			<p><%= errorMessage %></p>
  			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
    <%
        }
    %>
            <form action="TaskServlet" class="adt" method="post">


                <h1 class="title">Add Task</h1>

                <div class="input-field">
                    <label for="" class="lbl">User ID:</label>
                    <input required="" class="input" type="text" placeholder="Enter name" name="user_id" required/>
                </div>


                <div class="input-field"> 
                    <label for="" class="lbl">Project:</label>
                    <input required="" class="input" type="text" placeholder="Project Title" name="project" required/>
                </div>

                <div class="input-field">
                    <label for="" class="lbl">Date:</label>
                    <input required="" class="input" type="date" placeholder="DD-MM-YYYY" name="task_date" required />
                </div>

                <div class="input-field">
                    <label for="" class="lbl">Start:</label>
                    <input required="" class="input" type="time" placeholder="Duration" name="start_time" id="start_time" onchange = "CalculateDuration()"/>
                </div>

                <div class="input-field">
                    <label for="" class="lbl">End:</label>
                    <input required="" class="input" type="time" placeholder="Duration" name="end_time" id="end_time" onchange = "CalculateDuration()"/>
                </div>

                <div class="input-field">
                    <label for="" class="lbl">Duration:</label>
                    <input required="" class="input" type="text" placeholder="Task Duration" name="duration_time" id="duration"/>
                </div>


                <div class="input-field">
                    <label for="" class="lbl">Category:</label>
                    <input required="" class="input" type="text" placeholder="Task Category" name="category"/>
                </div>

                <div class="input-field">
                    <label for="" class="lbl">Description:</label>
                    <input required="" class="input" type="text" placeholder="Description" name="description"/>
                </div>

                <div class="btns">
                    <button class="cncl">Cancel</button>
                    <button class="atsk" type="submit" id="submit_btn">Add Task</button>
                </div>
            </form>
            
            <a href="AdminDashboard.jsp">Back to Dashboard</a>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script>
		var submit_btn = document.getElementById("submit_btn");
		
		submit_btn.disabled = true;
		function CalculateDuration(){
			var startTime = document.getElementById("start_time").value;
			var endTime = document.getElementById("end_time").value;
			
			
			
			if(startTime && endTime){
				var start = new Date("1970-01-01T" + startTime + ":00");
                var end = new Date("1970-01-01T" + endTime + ":00");
                
                if(start > end ){
                	alert("Start Time cannot be greater");
                }
				
				console.log(start);
				console.log(end);
				
				var difference = end - start;
				
				var duration = difference / (1000*60*60);
				
				document.getElementById("duration").value = duration.toFixed(0);
				
				if(document.getElementById("duration").value > 8){
					alert("Duration Cannot be greater than 8 hours")
					
				}
				else{
					submit_btn.disabled = false;
				}
				
			}
		}
	</script>
</body>

</html>