package ch.fhnw.sevenwonders.application;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ServerView {
	private Stage stage;
	private TextArea logArea;
	
	public ServerView(Stage inStage, TextArea inLogArea){
		BorderPane root = new BorderPane();
	    
		logArea = inLogArea;
		stage = inStage;
		
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);
        scrollPane.setContent(logArea);
        inLogArea.setWrapText(true);			
		
		Scene scene = new Scene(root,700,400);
		stage.setScene(scene);
		stage.show();
	}
	
	public void start() {
		stage.show();
	}
}
