package project_management.task_tracking.Json;

import java.awt.TextArea;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtils {
	
	private static final Logger logger = LogManager.getLogger(JsonUtils.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 6216260691258824277L;
	private String jsonFile;
	private int dayNumber;

	public JsonUtils(String dirPath) {
		setJsonConfiguration(dirPath);
		readJsonFile();
		
	}

	private final void setJsonConfiguration(String dirPath) {
		Calendar cal = Calendar.getInstance();
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		String month = months[cal.get(Calendar.MONTH)].toLowerCase();

		jsonFile = dirPath + month + cal.get(Calendar.YEAR) + ".json";

		// Recupération du jour de la date
		dayNumber = cal.get(Calendar.DAY_OF_MONTH);
	}

	private final void readJsonFile(){
		JSONParser parser = new JSONParser();
		
		Object obj;
		try {
			obj = parser.parse(new FileReader(jsonFile));
			
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray dayList = (JSONArray) jsonObject.get("dayList");
			
			JSONObject currentDay = (JSONObject) dayList.get(0);
			
			JSONArray dayTasks =  (JSONArray) currentDay.get("dayTasks");
			
			String tasks = "";
			
			for (int i = 0; i < dayTasks.size(); i++) {
				tasks += ((JSONObject)dayTasks.get(i)).get("task");
				tasks += "\n";
			}
			
			logger.trace(tasks);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
		
		
		
	}
	

	
	public boolean writeTextInCell(String dailyAction){
		boolean result = false;
//		getCurrentCell().setCellValue(dailyAction);
//
//		try {
//			FileOutputStream os = new FileOutputStream(xlsxFile);
//			getMyWorkBook().write(os);
//			os.close();
//			result = true;
//		} catch (FileNotFoundException e1) {
//			logger.error(e1.getMessage());
//		} catch (IOException e1) {
//			logger.error(e1.getMessage());
//		}
//		
		return result;
	}

}
