package ch.fhnw.sevenwonders.application;
	
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.fhnw.sevenwonders.model.Game;
import javafx.application.Application;
import javafx.stage.Stage;



public class ServerApplicationMain extends Application {
	private ServerView view;
	private Game game;
	
	@Override
	public void start(Stage primaryStage) {
		 // Registrieren des Loggers
        TextAreaHandler infoLogger = new TextAreaHandler();
        infoLogger.setLevel(Level.INFO);
        Logger defaultLogger = Logger.getLogger("");
        defaultLogger.addHandler(infoLogger);
        
        try {	        
        	game = new Game();
        	game.setDaemon(true);
        	game.start();
        	
			view = new ServerView(primaryStage, infoLogger.getTextArea());
			view.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args); 
	} 
}
