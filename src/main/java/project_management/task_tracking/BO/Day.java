package project_management.task_tracking.BO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Day {
	@JsonProperty("dayNumber")
	private String dayNumber;
	@JsonProperty("dayTasks")
	private List<Task> dayTasks;
	
	public Day() {
		dayNumber = "";
		dayTasks = new ArrayList<Task>();
	}
	public Day(String dayNumber, List<Task> dayTasks) {
		super();
		this.dayNumber = dayNumber;
		this.dayTasks = dayTasks;
	}
	public String getDayNumber() {
		return dayNumber;
	}
	public void setDayNumber(String dayNumber) {
		this.dayNumber = dayNumber;
	}
	@Override
	public String toString() {
		return "Day [dayNumber=" + dayNumber + ", dayTasks=" + dayTasks + "]";
	}
	public List<Task> getDayTasks() {
		return dayTasks;
	}
	public void setDayTasks(List<Task> dayTasks) {
		this.dayTasks = dayTasks;
	}
	
	
	
}
