package project_management.task_tracking.BO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonthJson {
	@JsonProperty("month")
	private String month;
	@JsonProperty("dayList")
	private List<DayJson> dayList;
	
	
	
	public MonthJson() {
		month = "";
		dayList = new ArrayList<DayJson>();
	}
	public MonthJson(String month, List<DayJson> dayList) {
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
	public List<DayJson> getDayList() {
		return dayList;
	}
	@Override
	public String toString() {
		return "MonthJson [month=" + month + ", dayList=" + dayList + "]";
	}
	public void setDayList(List<DayJson> dayList) {
		this.dayList = dayList;
	}
	
	public DayJson getDayByNumber(String dayNumber){
		DayJson result = null;
		for (int i = 0; i < dayList.size(); i++) {
			result = dayList.get(i);
			if(result.getDayNumber().equals(dayNumber)){
				return result;
			}
		}
		
		result = new DayJson(dayNumber);
		dayList.add(result);
		return result;
	}
	

}
