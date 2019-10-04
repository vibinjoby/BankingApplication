package com.bankingsystem.client.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bankingsystem.client.controller.ExistCustTransactionController;
import com.bankingsystem.client.controller.ExistingViewController;
import com.bankingsystem.client.controller.LoginViewController;
import com.bankingsystem.client.controller.MainViewController;
import com.bankingsystem.client.controller.NewCustViewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	public void authenticated() {
		showMainView();
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
			LoginViewController controller = loader.<LoginViewController>getController();
			controller.initManager(this);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void showMainView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("mainview.fxml"));
			scene.setRoot((Parent) loader.load());
			MainViewController controller = loader.<MainViewController>getController();
			controller.initManager(this,primaryStage);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void showNewCustomerPage() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("newcustomerpage.fxml"));
			scene.setRoot((Parent) loader.load());
			NewCustViewController controller = loader.<NewCustViewController>getController();
			controller.initManager(this);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void showExistingCustomerPage() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("existingcustomerpage.fxml"));
			scene.setRoot((Parent) loader.load());
			ExistingViewController controller = loader.<ExistingViewController>getController();
			controller.initManager(this);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void showExistingCustTransactionView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("custtransactionpage.fxml"));
			scene.setRoot((Parent) loader.load());
			ExistCustTransactionController controller = loader.<ExistCustTransactionController>getController();
			controller.initManager(this);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public Scene getScene() {
		return scene;
	}
}