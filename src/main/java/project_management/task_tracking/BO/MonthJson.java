package project_management.task_tracking.BO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonthJson {
	@JsonProperty("month")
	private String month;
	@JsonProperty("dayList")
	private List<Day> dayList;
	
	
	
	public MonthJson() {
		month = "";
		dayList = new ArrayList<Day>();
	}
	public MonthJson(String month, List<Day> dayList) {
		super();
		this.month = month;
		this.dayList = dayList;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public List<Day> getDayList() {
		return dayList;
	}
	@Override
	public String toString() {
		return "MonthJson [month=" + month + ", dayList=" + dayList + "]";
	}
	public void setDayList(List<Day> dayList) {
		this.dayList = dayList;
	}
	
	

}
