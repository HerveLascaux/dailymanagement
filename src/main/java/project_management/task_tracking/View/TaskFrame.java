package project_management.task_tracking.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;
import javax.swing.text.DefaultCaret;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import project_management.task_tracking.Excel.WriteInExcel;
import project_management.task_tracking.Json.JsonUtils;

public class TaskFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(TaskFrame.class);

	/**
	 * 
	 */
	private JTextArea textArea;
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
		
		setBackground(new Color(215,206,242));

	}
	
	private final void createFrameComponents(){
		// Creation des composant de la fenetre
				textArea = new JTextArea();
				textArea.setLineWrap(true);
				DefaultCaret caret = (DefaultCaret)textArea.getCaret();
				caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				JScrollPane scrollPane= new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scrollPane.setAutoscrolls(true);
				textArea.setBackground(new Color(215,206,242));
				textArea.setBorder(BorderFactory.createEmptyBorder());
				scrollPane.setBorder(BorderFactory.createEmptyBorder());
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
						
						if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
							setVisible(false);
							dispose();
						}
					}

					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
							setLastKey(-1);
						}
					}});
				
				
				JButton save = new JButton("Save");
				save.setBackground(Color.GREEN);
				save.setForeground(Color.BLACK);
				save.setBorder(BorderFactory.createLineBorder(new Color(215,206,242)));
				JButton cancel = new JButton("Cancel");
				cancel.setBackground(new Color(255,0,0));
				cancel.setForeground(Color.BLACK);
				cancel.setBorder(BorderFactory.createLineBorder(new Color(215,206,242)));
				BorderLayout firstLayout = new BorderLayout();
				JPanel textContainer = new JPanel();
				JPanel buttonContainer = new JPanel();
				buttonContainer.setBackground(new Color(238,238,238));
				setLayout(firstLayout);
				buttonContainer.setLayout(new GridLayout(1,2));
				buttonContainer.add(cancel);
				buttonContainer.add(save);
				buttonContainer.setPreferredSize(new Dimension(10,30));
				textContainer.setLayout(new BorderLayout());
				textContainer.add("Center", scrollPane);
				textContainer.setPreferredSize(new Dimension(10,80));
				add("Center", textContainer);
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

	public final JTextArea getTextArea() {
		return textArea;
	}

	public void actionPerformed(ActionEvent e) {
		String dailyAction = getTextArea().getText();
		excelWritter.writeTextInCell(dailyAction);

		setVisible(false);
		dispose();
	}
	
}
