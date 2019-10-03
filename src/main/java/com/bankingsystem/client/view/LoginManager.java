package com.bankingsystem.client.view;

import java.io.IOException;
import java.util.logging.*;

import com.bankingsystem.client.controller.LoginController;
import com.bankingsystem.client.controller.MainViewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/** Manages control flow for logins */
public class LoginManager {
	private Scene scene;
	private Stage primaryStage;

	public LoginManager(Scene scene,Stage primaryStage) {
		this.scene = scene;
		this.primaryStage = primaryStage;
	}

	/**
	 * Callback method invoked to notify that a user has been authenticated. Will
	 * show the main application screen.
	 */
	public void authenticated(String sessionID) {
		showMainView(sessionID);
	}

	/**
	 * Callback method invoked to notify that a user has logged out of the main
	 * application. Will show the login application screen.
	 */
	public void logout() {
		showLoginScreen();
	}

	public void showLoginScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("loginpage.fxml"));
			scene.setRoot((Parent) loader.load());
			LoginController controller = loader.<LoginController>getController();
			controller.initManager(this);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void showMainView(String sessionID) {
		try {
			System.out.println("Inside main");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("mainview.fxml"));
			scene.setRoot((Parent) loader.load());
			MainViewController controller = loader.<MainViewController>getController();
			controller.initManager(this,primaryStage);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void showNewCustomerPage(Stage primaryStage2) {
		primaryStage.setTitle("Banking Application System");
		// Create the registration form grid pane
		GridPane gridPane = NewCustomerFirstPage.createRegistrationFormPane();
		// Add UI controls to the registration form grid pane
		NewCustomerFirstPage.addUIControls(gridPane,this);
		// Create a scene with registration form grid pane as the root node
		Scene scene = new Scene(gridPane, 800, 500);
		// Set the scene in primary stage
		primaryStage.setScene(scene);
		NewCustomerFirstPage.primaryStage = primaryStage2;
		primaryStage.show();
	}
}