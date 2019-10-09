package com.bankingsystem.client.controller;

import com.bankingsystem.client.view.LoginManager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainViewController {
	@FXML
	private Button logoutBtn;

	@FXML
	private Button newCustomerBtn;

	@FXML
	private Button existingCustomerBtn;

	public void initManager(final LoginManager loginManager) {
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
