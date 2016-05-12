package project_management.task_tracking.BO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DayJson {
	@JsonProperty("dayNumber")
	private String dayNumber;
	@JsonProperty("dayTasks")
	private List<Task> dayTasks;
	
	public DayJson() {
		dayNumber = "";
		dayTasks = new ArrayList<Task>();
	}
	
	public DayJson(String dayNumber) {
		this.dayNumber = dayNumber;
		this.dayTasks = new ArrayList<Task>();
	}
	public DayJson(String dayNumber, List<Task> dayTasks) {
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
