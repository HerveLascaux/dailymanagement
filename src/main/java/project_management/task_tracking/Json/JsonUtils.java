package project_management.task_tracking.Json;

import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project_management.task_tracking.BO.DayJson;
import project_management.task_tracking.BO.MonthJson;
import project_management.task_tracking.BO.Task;

public class JsonUtils {
	
	private static final Logger logger = LogManager.getLogger(JsonUtils.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 6216260691258824277L;
	private String jsonFile;
	private int dayNumber;
	
	//JSON readed
	private MonthJson jsonMonth;
	private DayJson currentDay;
	
	public JsonUtils(String dirPath) {
		setJsonConfiguration(dirPath);
	}

	private final void setJsonConfiguration(String dirPath) {
		Calendar cal = Calendar.getInstance();
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		String month = months[cal.get(Calendar.MONTH)].toLowerCase();

		jsonFile = dirPath + month + cal.get(Calendar.YEAR) + ".json";

		// Recupération du jour courant
		dayNumber = cal.get(Calendar.DAY_OF_MONTH);
	}

	public String readJsonFile(){
		String result ="";
		File currentJsonFile = new File(jsonFile);
		if(!currentJsonFile.exists()){
			initJsonFile(currentJsonFile);
		}else{
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				jsonMonth = mapper.readValue(currentJsonFile, MonthJson.class);

				currentDay = jsonMonth.getDayByNumber(Integer.toString(dayNumber));
				List<Task> dayTasks = currentDay.getDayTasks();
				
				if(dayTasks.isEmpty()){
					return result;
				}else{
					for (int i = 0; i < dayTasks.size(); i++) {
						if(i!=0){
							result+="\n";
						}
						result+= dayTasks.get(i).getText();
					}
					return result;					
				}
				
			} catch (JsonParseException e1) {
				// TODO Auto-generated catch block
				logger.error(e1.getMessage());
			} catch (JsonMappingException e1) {
				// TODO Auto-generated catch block
				logger.error(e1.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error(e1.getMessage());
			}
		}
		return result;
		
	}
	

	
	private void initJsonFile(File currentJsonFile) {
		List<DayJson> dayList = new ArrayList<DayJson>();
		Calendar c = Calendar.getInstance();
		for (int i = 1; i < c.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			dayList.add(new DayJson(Integer.toString(i)));			
		}
			
		jsonMonth = new MonthJson(c.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.FRANCE), dayList);
		currentDay = jsonMonth.getDayByNumber(Integer.toString(c.get(Calendar.DAY_OF_MONTH)));
		
		
		writeJsonToFile("");
	}

	public boolean writeJsonToFile(String tasks){
		currentDay.setDayTasks(stringToTasks(tasks));
		
		boolean result = false;
		ObjectMapper mapper = new ObjectMapper();
		try {

			mapper.writeValue(new File(jsonFile), jsonMonth);
			result=true;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return result;
	}
	
	private List<Task> stringToTasks(String tasks){
		String[] tasksArray = tasks.split("\n");
		List<Task> dayTasks = new ArrayList<Task>();
		
		for (int i = 0; i < tasksArray.length; i++) {
			dayTasks.add(new Task(tasksArray[i]));
		}
		
		return dayTasks;
	}
	

}
