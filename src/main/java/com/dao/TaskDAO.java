package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Task;
import com.dao.DAO;

public class TaskDAO {
	
	private static final String INSERT_TASK_SQL = "INSERT INTO tasks (user_id, project, task_date, start_time, end_time, duration, category, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SELECT_TASK_SQL = "SELECT * FROM tasks";
	private static final String SELECT_TASK_USER_SQL = "SELECT * FROM tasks where user_id = ? ORDER BY project";
	private static final String CHECK_TASK_SQL = "SELECT * FROM tasks WHERE user_id = ? AND task_date = ? AND (start_time < ? AND end_time > ?)";
	private static final String SELECT_DAILY_TASKS_SQL = "SELECT * FROM tasks WHERE user_id = ? AND task_date = ?";
	private static final String SELECT_WEEKLY_TASKS_SQL = "SELECT * FROM tasks WHERE user_id = ? AND task_date>= CURDATE() - INTERVAL 7 DAY";
	private static final String SELECT_MONTHLY_TASKS_SQL = "SELECT * FROM tasks WHERE user_id = ? AND MONTH(task_date) = MONTH(CURDATE()) AND YEAR(task_date) = YEAR(CURDATE())";
	private static final String SELECT_TASKS_SQL = "SELECT * FROM tasks";
	private static final String UPDATE_TASK_SQL = "UPDATE tasks SET user_id = ?, project = ?, task_date = ?, start_time = ?, end_time = ?, duration = ?, category = ?, description = ? WHERE task_id = ?";
	
	public boolean addtask(Task task) throws SQLException {
		Connection connection = null;
		try{
			connection = DAO.getConnection();
			PreparedStatement insertStmt = connection.prepareStatement(INSERT_TASK_SQL);
			
			int affectedRows;
			
			insertStmt.setInt(1,task.getUserId());
			insertStmt.setString(2,task.getProject());
			insertStmt.setDate(3,task.getTaskDate());
			insertStmt.setString(4,task.getStartTime());
			insertStmt.setString(5,task.getEndTime());
			insertStmt.setInt(6,task.getDuration());
			insertStmt.setString(7,task.getCategory());
			insertStmt.setString(8,task.getDescription());
			
			 
			affectedRows = insertStmt.executeUpdate();
			
			if(affectedRows > 0) {
				return true;
			}
			else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}
	}
	
	public boolean checkTimeOverlap(int userId, java.sql.Date date, String startTime, String endTime) throws SQLException {
		Connection connection = null;
        try{
        	connection = DAO.getConnection();
            PreparedStatement stmt = connection.prepareStatement(CHECK_TASK_SQL);
            stmt.setInt(1, userId);
            stmt.setDate(2, date);
            stmt.setString(3, endTime);  
            stmt.setString(4, startTime); 
            ResultSet rs = stmt.executeQuery();
            return rs.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	connection.close();
        }
        return false;
    }
	
	
	public boolean editTask(Task task) throws SQLException {
		Connection connection = null;
		try{
			connection = DAO.getConnection();
			PreparedStatement insertStmt = connection.prepareStatement(UPDATE_TASK_SQL);
			int affectedRows;
			
			insertStmt.setInt(1,task.getUserId());
			insertStmt.setString(2,task.getProject());
			insertStmt.setDate(3,task.getTaskDate());
			insertStmt.setString(4,task.getStartTime());
			insertStmt.setString(5,task.getEndTime());
			insertStmt.setInt(6,task.getDuration());
			insertStmt.setString(7,task.getCategory());
			insertStmt.setString(8,task.getDescription());
			insertStmt.setInt(9, task.getTaskId());
			 
			affectedRows = insertStmt.executeUpdate();
			
			if(affectedRows > 0) {
				return true;
			}
			else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}
	}
	public List<Task> getTaskDetails(){
		List<Task> tasks = new ArrayList<>();
		 Connection conn = null;
	     PreparedStatement taskDetailsStmt = null;
	     ResultSet rs = null;
		
		try{
			conn = DAO.getConnection();
			taskDetailsStmt = conn.prepareStatement(SELECT_TASK_SQL);
			rs = taskDetailsStmt.executeQuery();
			
			while(rs.next()) {
				int task_id = rs.getInt("task_id");
				int user_id = rs.getInt("user_id");
				String project = rs.getString("project");
				Date task_date = rs.getDate("task_date");
				String start_time = rs.getString("start_time");
				String end_time = rs.getString("end_time");
				int duration = rs.getInt("duration");
				String category = rs.getString("category");
				String description = rs.getString("description");
				
				Task task = new Task(task_id,user_id,project,task_date,start_time,end_time,duration,category,description);
				tasks.add(task);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DAO.closeResources(conn,taskDetailsStmt,rs);
		}
		
		return tasks;
	}
	
	public Map<String,List<Task>> getTaskDetailsByAssociate(int userId){
		Map<String,List<Task>> tasks = new HashMap<>();
		 Connection conn = null;
	     PreparedStatement taskDetailsByUserStmt = null;
	     ResultSet rs = null;
		
		try{
			conn = DAO.getConnection();
			taskDetailsByUserStmt = conn.prepareStatement(SELECT_TASK_USER_SQL);
			taskDetailsByUserStmt.setInt(1, userId);
			rs = taskDetailsByUserStmt.executeQuery();
			
			while(rs.next()) {
				Task task = new Task();
				task.setTaskId(rs.getInt("task_id"));
				task.setUserId(rs.getInt("user_id"));
				task.setProject(rs.getString("project"));
				task.setTaskDate(rs.getDate("task_date"));
				task.setStartTime(rs.getString("start_time"));
				task.setEndTime(rs.getString("end_time"));
				task.setDuration(rs.getInt("duration"));
				task.setCategory(rs.getString("category"));
				task.setDescription(rs.getString("description"));
				
				tasks.computeIfAbsent(task.getProject(), k-> new ArrayList<>()).add(task);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DAO.closeResources(conn,taskDetailsByUserStmt,rs);
		}
		return tasks;
	}
	
	public List<Task> getDailyTasks(int user_id){
		List<Task> tasks = new ArrayList<>();
		
		LocalDate today = LocalDate.now();
		
		Connection conn = null;
		PreparedStatement dailyTasksStmt = null;
		ResultSet rs = null; 
		
		try {
				conn = DAO.getConnection();
				dailyTasksStmt = conn.prepareStatement(SELECT_DAILY_TASKS_SQL);

				dailyTasksStmt.setInt(1, user_id);
				dailyTasksStmt.setDate(2, Date.valueOf(today));

	            rs = dailyTasksStmt.executeQuery();

	            while (rs.next()) {
	                Task task = new Task();
	                task.setTaskId(rs.getInt("task_id"));
	                task.setUserId(rs.getInt("user_id"));
	                task.setProject(rs.getString("project"));
	                task.setTaskDate(rs.getDate("task_date"));
	                task.setStartTime(rs.getString("start_time"));
	                task.setEndTime(rs.getString("end_time"));
	                task.setDuration(rs.getInt("duration"));
	                task.setCategory(rs.getString("category"));
	                task.setDescription(rs.getString("description"));

	                tasks.add(task);
	            }
	            
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	        	DAO.closeResources(conn,dailyTasksStmt,rs);
	        }
	        return tasks;
	    }
	
	public List<Task> getWeeklyTasks(int user_id){
		List<Task> tasks = new ArrayList<>();
		Connection conn = null;
		PreparedStatement weeklyTasksStmt = null;
		ResultSet rs = null;
		
		try{
			conn = DAO.getConnection();
            weeklyTasksStmt = conn.prepareStatement(SELECT_WEEKLY_TASKS_SQL);
			
			weeklyTasksStmt.setInt(1, user_id);
			rs = weeklyTasksStmt.executeQuery();
			while (rs.next()) {
                Task task = new Task();
                task.setTaskId(rs.getInt("task_id"));
                task.setUserId(rs.getInt("user_id"));
                task.setProject(rs.getString("project"));
                task.setTaskDate(rs.getDate("task_date"));
                task.setStartTime(rs.getString("start_time"));
                task.setEndTime(rs.getString("end_time"));
                task.setDuration(rs.getInt("duration"));
                task.setCategory(rs.getString("category"));
                task.setDescription(rs.getString("description"));
                tasks.add(task);
            }
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DAO.closeResources(conn,weeklyTasksStmt,rs);
		}
		
		return tasks;
	}

	public List<Task> getMonthlyTasks(int user_id) {
		List<Task> tasks = new ArrayList<>();
		Connection conn = null;
		PreparedStatement monthlyTasksStmt = null;
		ResultSet rs = null;
		
		try{
			conn = DAO.getConnection();
            monthlyTasksStmt = conn.prepareStatement(SELECT_WEEKLY_TASKS_SQL);
			monthlyTasksStmt.setInt(1, user_id);
			rs = monthlyTasksStmt.executeQuery();
			while (rs.next()) {
                Task task = new Task();
                task.setTaskId(rs.getInt("task_id"));
                task.setUserId(rs.getInt("user_id"));
                task.setProject(rs.getString("project"));
                task.setTaskDate(rs.getDate("task_date"));
                task.setStartTime(rs.getString("start_time"));
                task.setEndTime(rs.getString("end_time"));
                task.setDuration(rs.getInt("duration"));
                task.setCategory(rs.getString("category"));
                task.setDescription(rs.getString("description"));
                tasks.add(task);
            }
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DAO.closeResources(conn,monthlyTasksStmt,rs);
		}
		
		return tasks;
	}

	public List<Task> getTasks() {
		List<Task> tasks = new ArrayList<>();
		Connection conn = null;
		PreparedStatement TasksStmt = null;
		ResultSet rs = null;
			
			try{
				conn = DAO.getConnection();
	             TasksStmt = conn.prepareStatement(SELECT_TASKS_SQL);
				
				rs = TasksStmt.executeQuery();
				while (rs.next()) {
	                Task task = new Task();
	                task.setTaskId(rs.getInt("task_id"));
	                task.setUserId(rs.getInt("user_id"));
	                task.setProject(rs.getString("project"));
	                task.setTaskDate(rs.getDate("task_date"));
	                task.setStartTime(rs.getString("start_time"));
	                task.setEndTime(rs.getString("end_time"));
	                task.setDuration(rs.getInt("duration"));
	                task.setCategory(rs.getString("category"));
	                task.setDescription(rs.getString("description"));
	                tasks.add(task);
	            }
				
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				DAO.closeResources(conn,TasksStmt,rs);
			}
		
		return tasks;
	}
}

