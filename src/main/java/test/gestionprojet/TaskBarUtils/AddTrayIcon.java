package test.gestionprojet.TaskBarUtils;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import test.gestionprojet.ExcelUtils.WriteInExcel;

public class AddTrayIcon {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Toolkit tlk = Toolkit.getDefaultToolkit();
		
		 if (!SystemTray.isSupported()) {
	            System.out.println("SystemTray is not supported");
	            return;
	        }
		 final TrayIcon trayIcon = new TrayIcon(tlk.createImage("images/bulb.gif"));
		 final PopupMenu popup = new PopupMenu();
		 final SystemTray tray = SystemTray.getSystemTray();
		
		 
		 MouseListener listener = new MouseListener()
		    {
		      

				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					new WriteInExcel();
				}

				public void mousePressed(MouseEvent e) {				}

				public void mouseReleased(MouseEvent e) {				}

				public void mouseEntered(MouseEvent e) {				}

				public void mouseExited(MouseEvent e) {					}				
		    };
		    
		    
		 trayIcon.addMouseListener(listener);
		 
		 try {
	            tray.add(trayIcon);
	        } catch (AWTException e) {
	            System.out.println("TrayIcon could not be added.");
	        }
		
	}

}
