package project_management.task_tracking.Main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import project_management.task_tracking.TaskBarUtils.AddTrayIcon;


public class TaskTracking {
	
	private static final Logger logger = LogManager.getLogger(TaskTracking.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		logger.trace("Lancement de l'application de gestion des tâches journalières");
		new AddTrayIcon();
		
	}

}
