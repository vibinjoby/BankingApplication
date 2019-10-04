package com.bankingsystem.client.controller;

import com.bankingsystem.client.view.LoginManager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ExistingViewController {

	@FXML
	private TextField debitCrdNum;

	@FXML
	private TextField mothMaidenName;

	@FXML
	private TextField emailAddr;

	@FXML
	private Button logoutBtn;

	@FXML
	private Button backBtn;

	@FXML
	private Button submitBtn;

	@FXML
	private Button resetBtn;

	public void initManager(final LoginManager loginManager) {
		logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginManager.logout();
			}
		});

		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginManager.showMainView();
			}
		});

		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginManager.showExistingCustTransactionView();
			}
		});

		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				debitCrdNum.setText("");
				mothMaidenName.setText("");
				emailAddr.setText("");
			}
		});
	}

}
