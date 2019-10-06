package com.bankingsystem.client.controller;

import com.bankingsystem.client.view.LoginManager;
import com.bankingsystem.db.CustomerReadData;
import com.bankingsystem.db.CustomerReadWriteDataImpl;
import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.util.FrontEndUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ExistCustEntryViewController extends FrontEndUtils {

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
				CustomerReadData readData = new CustomerReadWriteDataImpl();

				if (!debitCrdNum.getText().isEmpty() && !mothMaidenName.getText().isEmpty() && !emailAddr.getText().isEmpty()) {
					CustomerDetails customerDetails = readData.getCustDetailsUsingDebitCard(
							debitCrdNum.getText(), mothMaidenName.getText(), emailAddr.getText());
					if (customerDetails != null)
						loginManager.showExistingCustTransactionView(customerDetails);
					else
						showAlert(AlertType.INFORMATION, loginManager.getScene().getWindow(), "Incorrect Details!!",
								"Please Try again");
				} else {
					showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), "Inputs Missing",
							"Please Enter values in all the Fields!!");
				}
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
