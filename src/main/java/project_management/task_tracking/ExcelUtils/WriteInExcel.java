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

public class WriteInExcel extends Frame {
	
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
		super();

		setExcelConfiguration(dirPath);
		setFrameConfiguration();
		createFrameComponents();
		readExcelFile();
		
		setVisible(true);
		
		
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
				save.addActionListener(new ActionListener() {
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

				// Creation du listener pour le bouton cancel
				cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}

				});		
				
			
				
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

}
