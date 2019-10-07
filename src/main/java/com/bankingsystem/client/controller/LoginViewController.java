package com.bankingsystem.client.controller;

import com.bankingsystem.client.view.LoginManager;
import com.bankingsystem.util.FrontEndUtils;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/** Controls the login screen */
public class LoginViewController extends FrontEndUtils{
	@FXML
	private TextField user;
	@FXML
	private TextField password;
	@FXML
	private Button loginButton;
	@FXML
	private Button resetButton;

	public void initManager(final LoginManager loginManager) {
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!user.getText().isEmpty() && !password.getText().isEmpty()) {
					String sessionID = authorize();
					if (sessionID != null) {
						loginManager.authenticated();
					}
				} else {
					showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), "No Inputs Found",
							"Please Enter all the inputs!!!");
				}
			}
		});

		resetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				user.setText("");
				password.setText("");
			}
		});
	}

	/**
	 * Check authorization credentials.
	 * 
	 * If accepted, return a sessionID for the authorized session otherwise, return
	 * null.
	 */
	private String authorize() {
		return "open".equals(user.getText()) && "sesame".equals(password.getText()) ? generateSessionID() : null;
	}

	private static int sessionID = 0;

	private String generateSessionID() {
		sessionID++;
		return "xyzzy - session " + sessionID;
	}
}
