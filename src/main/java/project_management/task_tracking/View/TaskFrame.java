package project_management.task_tracking.View;

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

import project_management.task_tracking.Excel.WriteInExcel;
import project_management.task_tracking.Json.JsonUtils;

public class TaskFrame extends Frame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(TaskFrame.class);

	/**
	 * 
	 */
	private Cell currentCell;
	private FileInputStream fis;
	private TextArea textArea;
	private XSSFWorkbook myWorkBook;
	private String xlsxFile;
	private int dayNumber;
	private int lastKey;
	
	private JsonUtils jsonutils;

	private WriteInExcel excelWritter;
	
	public TaskFrame(String dirPath) {
		super();
		
		// Handle Excel
		excelWritter = new WriteInExcel(dirPath);
		String tasks = excelWritter.readExcelFile();
		
		// Handle JSON
		jsonutils = new JsonUtils(dirPath);
		
		setFrameConfiguration();
		createFrameComponents();
		
		
		textArea.setText(tasks);
		textArea.setCaretPosition(tasks.length());
		
		setVisible(true);
		setAlwaysOnTop(true);
	}


	private final void setFrameConfiguration() {
		// Mise en place des propriétés pour la fenetre
		setTitle("Task tracking");
		setSize(300, 150);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// height of the task bar
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(
				getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		setLocation(screenSize.width - getWidth(), screenSize.height
				- taskBarSize - getHeight());
		setUndecorated(true);

	}
	
	private final void createFrameComponents(){
		// Creation des composant de la fenetre
				textArea = new TextArea("", 0,0, TextArea.SCROLLBARS_NONE);
				textArea.setBackground(new Color(250,250,205));
				//Ajout d'un listener pour enregistrer quand on appuie sur CTRL+ENTER
				textArea.addKeyListener(new KeyListener(){

					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}

					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						if(getLastKey() == KeyEvent.VK_CONTROL && e.getKeyCode()== KeyEvent.VK_ENTER){
							
							String dailyAction = getTextArea().getText();
							excelWritter.writeTextInCell(dailyAction);
							setVisible(false);
							dispose();
						}
						if(e.getKeyCode() == KeyEvent.VK_CONTROL){
							setLastKey(e.getKeyCode());
						}
					}

					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
							setLastKey(-1);
						}
					}});
				
				
				Button save = new Button("Save");
				Button cancel = new Button("Cancel");
				BorderLayout firstLayout = new BorderLayout();
				Panel textContainer = new Panel();
				Panel buttonContainer = new Panel();
				buttonContainer.setBackground(new Color(238,238,238));
				setLayout(firstLayout);
				buttonContainer.setLayout(new FlowLayout());
				buttonContainer.add(cancel);
				buttonContainer.add(save);
				textContainer.add(textArea);
				add("Center", textArea);
				add("South", buttonContainer);

				// Creation du listener pour le bouton save
				save.addActionListener(this);

				// Creation du listener pour le bouton cancel
				cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}

				});		
				
			
				
	}
	
	public int getLastKey() {
		return lastKey;
	}

	public void setLastKey(int lastKey) {
		this.lastKey = lastKey;
	}

	public final TextArea getTextArea() {
		return textArea;
	}

	public void actionPerformed(ActionEvent e) {
		String dailyAction = getTextArea().getText();
		excelWritter.writeTextInCell(dailyAction);

		setVisible(false);
		dispose();
	}
	
}
