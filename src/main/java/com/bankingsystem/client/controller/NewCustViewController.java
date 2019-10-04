package com.bankingsystem.client.controller;

import com.bankingsystem.client.view.LoginManager;
import com.bankingsystem.db.CustomerReadWriteData;
import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.CustomerName;
import com.bankingsystem.model.ErrorDetails;
import com.bankingsystem.util.FrontEndUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class NewCustViewController extends FrontEndUtils {

	@FXML
	private TextField firstName;

	@FXML
	private TextField middleName;

	@FXML
	private TextField lastName;

	@FXML
	private TextField emailAddr;

	@FXML
	private DatePicker dateOfBirth;

	@FXML
	private CheckBox checkBoxSav;

	@FXML
	private CheckBox checkBoxCheq;

	@FXML
	private CheckBox checkBoxStud;

	@FXML
	private TextField mothMaidenName;

	@FXML
	private TextField sinNumber;

	@FXML
	private TextField pinNumber;

	@FXML
	private Button logoutBtn;

	@FXML
	private Button submitBtn;

	@FXML
	private Button resetBtn;

	@FXML
	private Button backBtn;

	public void initManager(final LoginManager loginManager) {

		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CustomerDetails custDetails = new CustomerDetails(null, null, null, emailAddr.getText(),
						(dateOfBirth != null && dateOfBirth.getValue() != null) ? String.valueOf(dateOfBirth.getValue()): null,
						null, null, null, null, false, false, false);
				custDetails.setName(new CustomerName(firstName.getText(), middleName.getText(), lastName.getText()));
				addCustDetails(custDetails, checkBoxSav, checkBoxCheq, checkBoxStud, mothMaidenName, sinNumber,
						pinNumber);
				ErrorDetails error = CustomerReadWriteData.addNewCustomer(custDetails);

				if (error == null) {
					showAlert(AlertType.INFORMATION, loginManager.getScene().getWindow(), "Registration Successful! ",
							"\n Mr. " + custDetails.getName().getFirstName() + "Your Account Number is: "
									+ custDetails.getAccountNumber());
					loginManager.showMainView();
				} else
					showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), "Registration Failed!!",
							"\n" + error.getErrorDescription());
			}
		});

		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginManager.showMainView();
			}
		});

		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				firstName.setText("");
				middleName.setText("");
				lastName.setText("");
				emailAddr.setText("");
				checkBoxSav.setSelected(false);
				checkBoxCheq.setSelected(false);
				checkBoxStud.setSelected(false);
				mothMaidenName.setText("");
				sinNumber.setText("");
				pinNumber.setText("");
				firstName.setText("");
			}
		});
	}

}
