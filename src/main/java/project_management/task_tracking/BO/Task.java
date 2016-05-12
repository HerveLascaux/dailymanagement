package project_management.task_tracking.BO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class Task {

	@JsonProperty("text")
	private String text;
	@JsonProperty("time")
	private String time;

	public Task(String text, String time) {
		this.text = text;
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Task() {
		text ="";
	}

	public Task(String text) {
		this.text = text;
		this.time = "";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Task [text=" + text + "]";
	}
	
	
}
