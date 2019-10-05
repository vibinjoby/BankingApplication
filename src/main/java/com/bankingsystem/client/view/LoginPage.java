package com.bankingsystem.client.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane gridPane = new GridPane();
		Scene scene = new Scene(gridPane,770,700);
		
		LoginManager loginManager = new LoginManager(scene,primaryStage);
		loginManager.showLoginScreen();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
