package com.bankingsystem.client.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bankingsystem.client.controller.ExistCustTransactionController;
import com.bankingsystem.client.controller.ExistCustEntryViewController;
import com.bankingsystem.client.controller.LoginViewController;
import com.bankingsystem.client.controller.MainViewController;
import com.bankingsystem.client.controller.NewCustViewController;
import com.bankingsystem.model.CustomerDetails;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Manages control flow for logins */
public class LoginManager {
	private Scene scene;
	public Stage primaryStage;

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

	/**
	 * Method to show Login Screen
	 */
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

	/**
	 * Method to show main view page after logging in
	 */
	public void showMainView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("mainview.fxml"));
			scene.setRoot((Parent) loader.load());
			MainViewController controller = loader.<MainViewController>getController();
			controller.initManager(this);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Method to show new customer page
	 */
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

	/**
	 * Method to show existing customer page
	 */
	public void showExistingCustomerPage() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("exist_customer_entrypage.fxml"));
			scene.setRoot((Parent) loader.load());
			ExistCustEntryViewController controller = loader.<ExistCustEntryViewController>getController();
			controller.initManager(this);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * @param customerDetails
	 * Method to show existing customer transaction page
	 */
	public void showExistingCustTransactionView(CustomerDetails customerDetails) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("exist_customer_transactionpage.fxml"));
			scene.setRoot((Parent) loader.load());
			ExistCustTransactionController controller = loader.<ExistCustTransactionController>getController();
			controller.initManager(this,customerDetails);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public Scene getScene() {
		return scene;
	}
}