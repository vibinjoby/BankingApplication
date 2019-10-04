package com.bankingsystem.client.controller;

import com.bankingsystem.client.view.LoginManager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainViewController {
	@FXML
	private Button logoutBtn;

	@FXML
	private Button newCustomerBtn;

	@FXML
	private Button existingCustomerBtn;

	@FXML
	private Button activateCreditCrdBtn;

	public void initManager(final LoginManager loginManager,final Stage primaryStage) {
		newCustomerBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginManager.showNewCustomerPage();
			}
		});

		existingCustomerBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginManager.showExistingCustomerPage();
			}
		});
		logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginManager.logout();
			}
		});
	}

}
