package project_management.task_tracking.TaskBarUtils;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import project_management.task_tracking.ExcelUtils.WriteInExcel;
import project_management.task_tracking.View.TaskFrame;

public class AddTrayIcon {

	private static final Logger logger = LogManager.getLogger(AddTrayIcon.class);

	private static String dirPath;
	
	private Frame currentFrame;

	public static String getDirPath() {
		return dirPath;
	}

	public static void setDirPath(String dirPath) {
		AddTrayIcon.dirPath = dirPath;
	}

	public AddTrayIcon() {

		loadProperties();

		Toolkit tlk = Toolkit.getDefaultToolkit();

		if (!SystemTray.isSupported()) {
			logger.error("SystemTray is not supported");
			return;
		}

		Image icon = tlk.createImage("images/task_tracking.png");
		final TrayIcon trayIcon = new TrayIcon(icon);
		final SystemTray tray = SystemTray.getSystemTray();

		MouseListener listener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
					if(currentFrame == null || !currentFrame.isDisplayable()){
						currentFrame = new TaskFrame(getDirPath());
					}else{
						logger.trace("A frame is already opened");
					}
				} else if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
					logger.trace("Fermeture du programme !");
					tray.remove(trayIcon);
					System.exit(0);
				}
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		};

		trayIcon.addMouseListener(listener);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			logger.error("TrayIcon could not be added.");
		}

	}

	private void loadProperties() {

		File file = new File("conf/application.properties");
		FileInputStream fileInput;
		Properties properties = new Properties();
		try {
			fileInput = new FileInputStream(file);
			properties.load(fileInput);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		dirPath = properties.getProperty("directory.location");

	}

}
