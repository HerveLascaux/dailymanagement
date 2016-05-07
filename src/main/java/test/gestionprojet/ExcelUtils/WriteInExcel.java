package test.gestionprojet.ExcelUtils;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteInExcel extends Frame {
	
	private Cell currentCell;
	private FileInputStream fis;
	private TextArea textArea;
	private XSSFWorkbook myWorkBook;
	private String xlsxFile;

	public WriteInExcel() {
		super();
		
		
		//Recupération du jour de la date
		int dayNumber = Calendar.DAY_OF_MONTH;
		
		//Mise en place des propriétés pour la fenetre
		setSize(300, 150);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//height of the task bar
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;		
		setLocation(screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());
		setUndecorated(true);
		
		
		xlsxFile = "D:\\Temp\\test.xlsx";
		
		//Creation des composant de la fenetre
		textArea = new TextArea();
		Button save = new Button("Save");
		Button cancel = new Button("Cancel");		
		BorderLayout firstLayout= new BorderLayout();
		Panel textContainer = new Panel();
		Panel buttonContainer = new Panel();
		setLayout(firstLayout);
		buttonContainer.setLayout(new FlowLayout());
		buttonContainer.add(cancel);
		buttonContainer.add(save);
		textContainer.add(textArea);
		add("Center",textArea);
		add("South",buttonContainer);

		//Creation du listener pour le bouton save
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String dailyAction = getTextArea().getText();
				getCurrentCell().setCellValue(dailyAction);

				try {
					FileOutputStream os = new FileOutputStream(xlsxFile);
					getMyWorkBook().write(os);
					os.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
				
				setVisible(false);
				dispose();
			}

		});
		
		//Creation du listener pour le bouton cancel
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}

		});
		
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(xlsxFile);
			myWorkBook = new XSSFWorkbook(fis);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);			
			
			
			Row currentRow = mySheet.getRow(dayNumber);
			if(currentRow == null) currentRow = mySheet.createRow(dayNumber);
			currentCell = currentRow.getCell(0);
			if(currentCell == null) currentCell = currentRow.createCell(0);
			else textArea.setText(currentCell.getStringCellValue());
			
			
			setVisible(true);
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Cell getCurrentCell() {
		return currentCell;
	}


	public FileInputStream getFis() {
		return fis;
	}


	public TextArea getTextArea() {
		return textArea;
	}


	public XSSFWorkbook getMyWorkBook() {
		return myWorkBook;
	}


	public String getXlsxFile() {
		return xlsxFile;
	}
	
	

}
