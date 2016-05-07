package project_management.task_tracking.ExcelUtils;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import project_management.task_tracking.TaskBarUtils.AddTrayIcon;

public class WriteInExcel  {
	
	private static final Logger logger = LogManager.getLogger(WriteInExcel.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 6216260691258824277L;
	private Cell currentCell;
	private FileInputStream fis;
	private TextArea textArea;
	private XSSFWorkbook myWorkBook;
	private String xlsxFile;
	private int dayNumber;
	private int lastKey;

	public WriteInExcel(String dirPath) {
		setExcelConfiguration(dirPath);
		readExcelFile();
		
	}

	private final void setExcelConfiguration(String dirPath) {
		Calendar cal = Calendar.getInstance();
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		String month = months[cal.get(Calendar.MONTH)].toLowerCase();

		xlsxFile = dirPath + month + cal.get(Calendar.YEAR) + ".xlsx";

		// Recupération du jour de la date
		dayNumber = cal.get(Calendar.DAY_OF_MONTH);
	}

	private final void readExcelFile(){
		FileInputStream fis;
		try {
			fis = new FileInputStream(xlsxFile);
			myWorkBook = new XSSFWorkbook(fis);
			XSSFSheet mySheet = myWorkBook.getSheetAt(1);

			Row currentRow = mySheet.getRow(dayNumber-1);
			if (currentRow == null){
				currentRow = mySheet.createRow(dayNumber-1);
			}
			currentCell = currentRow.getCell(0);
			if (currentCell == null){
				currentCell = currentRow.createCell(0);
			} else {
				textArea.setText(currentCell.getStringCellValue());
				textArea.setCaretPosition(textArea.getText().length());
			}


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}
	

	public final Cell getCurrentCell() {
		return currentCell;
	}

	public int getLastKey() {
		return lastKey;
	}

	public void setLastKey(int lastKey) {
		this.lastKey = lastKey;
	}

	public final FileInputStream getFis() {
		return fis;
	}

	public final TextArea getTextArea() {
		return textArea;
	}

	public final XSSFWorkbook getMyWorkBook() {
		return myWorkBook;
	}

	public final String getXlsxFile() {
		return xlsxFile;
	}
	
	public boolean writeTextInCell(String dailyAction){
		boolean result = false;
		getCurrentCell().setCellValue(dailyAction);

		try {
			FileOutputStream os = new FileOutputStream(xlsxFile);
			getMyWorkBook().write(os);
			os.close();
			result = true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return result;
	}

}
