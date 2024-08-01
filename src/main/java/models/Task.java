package models;

public class Task {
	private int UserID;
    private int taskId;
    private int userId;
    private String project;
    private java.sql.Date taskDate;
//    private java.sql.Time startTime;
//    private java.sql.Time endTime;
    private String startTime;
    private String endTime;
    private int duration;
    private String category;
    private String description;

    
    public Task() {}

    public Task(int taskId, int userId, String project, java.sql.Date taskDate, String startTime, String endTime, int duration, String category, String description) {
        this.taskId = taskId;
        this.userId = userId;
        this.project = project;
        this.taskDate = taskDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.category = category;
        this.description = description;
    }

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public java.sql.Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(java.sql.Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int f) {
		this.duration = f;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
    
}
