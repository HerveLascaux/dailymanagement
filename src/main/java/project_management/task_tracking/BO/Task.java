package project_management.task_tracking.BO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {

	@JsonProperty("text")
	private String text;

	public Task() {
		text ="";
	}

	public Task(String text) {
		super();
		this.text = text;
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
