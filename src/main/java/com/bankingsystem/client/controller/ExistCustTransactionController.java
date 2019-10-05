package com.bankingsystem.client.controller;

import com.bankingsystem.client.view.LoginManager;
import com.bankingsystem.db.CustomerReadWriteData;
import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.ErrorDetails;
import com.bankingsystem.util.FrontEndUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * https://o7planning.org/en/11533/opening-a-new-window-in-javafx
 * https://www.callicoder.com/javafx-registration-form-gui-tutorial/
 *
 */
public class ExistCustTransactionController extends FrontEndUtils {

	@FXML
	private Button depositBtn;

	@FXML
	private Label savLbl;

	@FXML
	private Label cheqLbl;

	@FXML
	private Label studLbl;

	@FXML
	private Button withdrawBtn;

	@FXML
	private Button transferBtn;

	@FXML
	private Button interacBtn;

	@FXML
	private Button accStmtBtn;

	@FXML
	private Button homeBtn;

	@FXML
	private Button hydroBtn;

	@FXML
	private Button phoneBtn;

	@FXML
	private Button submitBtn;

	@FXML
	private Button resetBtn;

	@FXML
	private TextField creditCrdNum;

	@FXML
	private TextField mothMaidenName;

	@FXML
	private Button logoutBtn;

	/**
	 * @param loginManager
	 * @param customerDetails
	 */
	public void initManager(final LoginManager loginManager, final CustomerDetails customerDetails) {

		savLbl.setText(FrontEndUtils.getSavingsBalanceDetails(customerDetails));
		cheqLbl.setText(FrontEndUtils.getChequingBalanceDetails(customerDetails));
		studLbl.setText(FrontEndUtils.getStudentBalanceDetails(customerDetails));

		if (customerDetails.isChequingAcc() && customerDetails.isSavingsAcc()
				|| customerDetails.isStudentAcc() && customerDetails.isSavingsAcc()
				|| customerDetails.isStudentAcc() && customerDetails.isChequingAcc()) {
			transferBtn.setDisable(false);
		} else {
			transferBtn.setDisable(true);
		}

		logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginManager.showLoginScreen();
			}
		});

		homeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginManager.showMainView();
			}
		});

		interacBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GridPane gridPane = createRegistrationFormPane();

				Label recepientName = new Label("Recepient Name :");
				gridPane.add(recepientName, 0, 1);

				final TextField recepientNameField = new TextField();
				recepientNameField.setPrefHeight(40);
				gridPane.add(recepientNameField, 1, 1);

				Label recepientEmailId = new Label("Recepient Email ID :");
				gridPane.add(recepientEmailId, 0, 2);

				final TextField recepientEmailField = new TextField();
				recepientEmailField.setPrefHeight(40);
				gridPane.add(recepientEmailField, 1, 2);

				Label payAmt = new Label("Pay Amount :");
				gridPane.add(payAmt, 0, 3);

				final TextField payAmtField = new TextField();
				payAmtField.setPrefHeight(40);
				gridPane.add(payAmtField, 1, 3);

				Label transferFromLbl = new Label("Transfer From :");
				gridPane.add(transferFromLbl, 0, 4);

				final ComboBox<String> accountTypeBox = new ComboBox<String>();
				if (customerDetails.isChequingAcc())
					accountTypeBox.getItems().add("Chequing");
				if (customerDetails.isSavingsAcc())
					accountTypeBox.getItems().add("Savings");
				if (customerDetails.isStudentAcc())
					accountTypeBox.getItems().add("Student");
				gridPane.add(accountTypeBox, 1, 4);

				Button interacBtnAcct = new Button("Interac");
				interacBtnAcct.setPrefHeight(40);
				interacBtnAcct.setDefaultButton(true);
				interacBtnAcct.setPrefWidth(100);
				gridPane.add(interacBtnAcct, 0, 6, 2, 1);
				GridPane.setHalignment(interacBtnAcct, HPos.CENTER);
				GridPane.setMargin(interacBtnAcct, new Insets(20, 0, 20, 0));

				Scene secondScene = new Scene(gridPane, 500, 300);

				// New window (Stage)
				final Stage newWindow = new Stage();
				newWindow.setTitle("Interac Money Transfer");
				newWindow.setScene(secondScene);

				// Set position of second window, related to primary window.
				newWindow.setX(loginManager.primaryStage.getX() + 100);
				newWindow.setY(loginManager.primaryStage.getY() + 100);

				newWindow.show();

				interacBtnAcct.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if (recepientNameField.getText().isEmpty() || recepientEmailField.getText().isEmpty()
								|| payAmtField.getText().isEmpty() || accountTypeBox.getValue()==null) {
							showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), "Inputs Missing",
									"Please Enter values in all the Fields!!");
						} else {
							int index = CustomerReadWriteData.customerDetailsList.indexOf(customerDetails);
							ErrorDetails error = CustomerReadWriteData.interacMoneyTransfer(
									recepientNameField.getText(), recepientEmailField.getText(), customerDetails,
									payAmtField.getText(), accountTypeBox.getValue());
							if (error != null) {
								showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), error.getErrorMessage(),
										error.getErrorDescription());
							} else {
								newWindow.close();
								showAlert(AlertType.CONFIRMATION, loginManager.getScene().getWindow(),
										"Interac Money Transfer", "Money Transferred Successfully!!");
								CustomerDetails updatedCustInfo = CustomerReadWriteData.customerDetailsList.get(index);
								loginManager.showExistingCustTransactionView(updatedCustInfo);
							}

						}
					}
				});
			}
		});

		transferBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GridPane gridPane = createRegistrationFormPane();

				Label transferFromLbl = new Label("Transfer From :");
				gridPane.add(transferFromLbl, 0, 1);

				final ComboBox<String> fromAccountTypeBox = new ComboBox<String>();
				if (customerDetails.isChequingAcc())
					fromAccountTypeBox.getItems().add("Chequing");
				if (customerDetails.isSavingsAcc())
					fromAccountTypeBox.getItems().add("Savings");
				if (customerDetails.isStudentAcc())
					fromAccountTypeBox.getItems().add("Student");
				gridPane.add(fromAccountTypeBox, 1, 1);

				Label transferToLbl = new Label("Transfer To :");
				gridPane.add(transferToLbl, 0, 2);

				final ComboBox<String> toAccountTypeBox = new ComboBox<String>();
				if (customerDetails.isChequingAcc())
					toAccountTypeBox.getItems().add("Chequing");
				if (customerDetails.isSavingsAcc())
					toAccountTypeBox.getItems().add("Savings");
				if (customerDetails.isStudentAcc())
					toAccountTypeBox.getItems().add("Student");
				gridPane.add(toAccountTypeBox, 1, 2);

				Label payAmt = new Label("Pay Amount :");
				gridPane.add(payAmt, 0, 3);

				final TextField payAmtField = new TextField();
				payAmtField.setPrefHeight(40);
				gridPane.add(payAmtField, 1, 3);

				Button transferBtnAccts = new Button("Transfer");
				transferBtnAccts.setPrefHeight(40);
				transferBtnAccts.setDefaultButton(true);
				transferBtnAccts.setPrefWidth(100);
				gridPane.add(transferBtnAccts, 0, 4, 2, 1);
				GridPane.setHalignment(transferBtnAccts, HPos.CENTER);
				GridPane.setMargin(transferBtnAccts, new Insets(20, 0, 20, 0));

				Scene secondScene = new Scene(gridPane, 500, 300);

				// New window (Stage)
				final Stage newWindow = new Stage();
				newWindow.setTitle("Transfer between Accounts");
				newWindow.setScene(secondScene);

				// Set position of second window, related to primary window.
				newWindow.setX(loginManager.primaryStage.getX() + 100);
				newWindow.setY(loginManager.primaryStage.getY() + 100);

				newWindow.show();

				transferBtnAccts.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						if (payAmtField.getText().isEmpty() || fromAccountTypeBox.getValue()==null
								|| toAccountTypeBox.getValue()==null) {
							showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), "Inputs Missing",
									"Please Enter values in all the Fields!!");
						} else {
							int index = CustomerReadWriteData.customerDetailsList.indexOf(customerDetails);
							ErrorDetails error = CustomerReadWriteData.transferBtwnAccts(customerDetails,
									payAmtField.getText(), fromAccountTypeBox.getValue(), toAccountTypeBox.getValue());
							if (error != null) {
								showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), error.getErrorMessage(),
										error.getErrorDescription());
							} else {
								newWindow.close();
								showAlert(AlertType.CONFIRMATION, loginManager.getScene().getWindow(),
										"Transfer between Accounts", "Money Transferred Successfully!!");
								CustomerDetails updatedCustInfo = CustomerReadWriteData.customerDetailsList.get(index);
								loginManager.showExistingCustTransactionView(updatedCustInfo);
							}
						}
					}

				});

			}
		});
		
		depositBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GridPane gridPane = createRegistrationFormPane();

				Label payToAcct = new Label("Pay To :");
				gridPane.add(payToAcct, 0, 1);
				
				final ComboBox<String> toAccountTypeBox = new ComboBox<String>();
				if (customerDetails.isChequingAcc())
					toAccountTypeBox.getItems().add("Chequing");
				if (customerDetails.isSavingsAcc())
					toAccountTypeBox.getItems().add("Savings");
				if (customerDetails.isStudentAcc())
					toAccountTypeBox.getItems().add("Student");
				gridPane.add(toAccountTypeBox, 1,1);
				
				Label payAmt = new Label("Pay Amount :");
				gridPane.add(payAmt, 0, 2);

				final TextField payAmtField = new TextField();
				payAmtField.setPrefHeight(40);
				gridPane.add(payAmtField, 1, 2);
				
				Button depositBtn = new Button("Deposit");
				depositBtn.setPrefHeight(40);
				depositBtn.setDefaultButton(true);
				depositBtn.setPrefWidth(100);
				gridPane.add(depositBtn, 0, 4, 2, 1);
				GridPane.setHalignment(depositBtn, HPos.CENTER);
				GridPane.setMargin(depositBtn, new Insets(20, 0, 20, 0));

				Scene secondScene = new Scene(gridPane, 500, 300);

				// New window (Stage)
				final Stage newWindow = new Stage();
				newWindow.setTitle("Deposit Money");
				newWindow.setScene(secondScene);

				// Set position of second window, related to primary window.
				newWindow.setX(loginManager.primaryStage.getX() + 100);
				newWindow.setY(loginManager.primaryStage.getY() + 100);

				newWindow.show();

				depositBtn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						if (toAccountTypeBox.getValue()==null || payAmtField.getText().isEmpty()) {
							showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), "Inputs Missing",
									"Please Enter values in all the Fields!!");
						} else {
							int index = CustomerReadWriteData.customerDetailsList.indexOf(customerDetails);
							ErrorDetails error = CustomerReadWriteData.depositMoney(customerDetails, payAmtField.getText(), toAccountTypeBox.getValue());
							if (error != null) {
								showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), error.getErrorMessage(),
										error.getErrorDescription());
							} else {
								newWindow.close();
								showAlert(AlertType.CONFIRMATION, loginManager.getScene().getWindow(),
										"Deposit Money", "Money Deposited Successfully!!");
								CustomerDetails updatedCustInfo = CustomerReadWriteData.customerDetailsList.get(index);
								loginManager.showExistingCustTransactionView(updatedCustInfo);
							}
						}
					}
				});
			}
		});
		
		withdrawBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GridPane gridPane = createRegistrationFormPane();

				Label withdrawFromAcct = new Label("Withdraw from :");
				gridPane.add(withdrawFromAcct, 0, 1);
				
				final ComboBox<String> fromAccountTypeBox = new ComboBox<String>();
				if (customerDetails.isChequingAcc())
					fromAccountTypeBox.getItems().add("Chequing");
				if (customerDetails.isSavingsAcc())
					fromAccountTypeBox.getItems().add("Savings");
				if (customerDetails.isStudentAcc())
					fromAccountTypeBox.getItems().add("Student");
				gridPane.add(fromAccountTypeBox, 1,1);
				
				Label withdrawAmt = new Label("Withdraw Amount :");
				gridPane.add(withdrawAmt, 0, 2);

				final TextField withdrawAmtField = new TextField();
				withdrawAmtField.setPrefHeight(40);
				gridPane.add(withdrawAmtField, 1, 2);
				
				Button withdrawBtn = new Button("Withdraw");
				withdrawBtn.setPrefHeight(40);
				withdrawBtn.setDefaultButton(true);
				withdrawBtn.setPrefWidth(100);
				gridPane.add(withdrawBtn, 0, 3, 2, 1);
				GridPane.setHalignment(withdrawBtn, HPos.CENTER);
				GridPane.setMargin(withdrawBtn, new Insets(20, 0, 20, 0));

				Scene secondScene = new Scene(gridPane, 500, 300);

				// New window (Stage)
				final Stage newWindow = new Stage();
				newWindow.setTitle("Withdraw Money");
				newWindow.setScene(secondScene);

				// Set position of second window, related to primary window.
				newWindow.setX(loginManager.primaryStage.getX() + 100);
				newWindow.setY(loginManager.primaryStage.getY() + 100);

				newWindow.show();

				withdrawBtn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						if (fromAccountTypeBox.getValue()==null || withdrawAmtField.getText().isEmpty()) {
							showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), "Inputs Missing",
									"Please Enter values in all the Fields!!");
						} else {
							int index = CustomerReadWriteData.customerDetailsList.indexOf(customerDetails);
							ErrorDetails error = CustomerReadWriteData.withdrawMoney(customerDetails, withdrawAmtField.getText(), fromAccountTypeBox.getValue());
							if (error != null) {
								showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), error.getErrorMessage(),
										error.getErrorDescription());
							} else {
								newWindow.close();
								showAlert(AlertType.CONFIRMATION, loginManager.getScene().getWindow(),
										"Withdraw Money", "Money Withdrawn Successfully!!");
								CustomerDetails updatedCustInfo = CustomerReadWriteData.customerDetailsList.get(index);
								loginManager.showExistingCustTransactionView(updatedCustInfo);
							}
						}
					}
				});
			}
		});
		
		hydroBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GridPane gridPane = createRegistrationFormPane();

				Label payFromAcct = new Label("Pay from :");
				gridPane.add(payFromAcct, 0, 1);
				
				final ComboBox<String> fromAccountTypeBox = new ComboBox<String>();
				if (customerDetails.isChequingAcc())
					fromAccountTypeBox.getItems().add("Chequing");
				if (customerDetails.isSavingsAcc())
					fromAccountTypeBox.getItems().add("Savings");
				if (customerDetails.isStudentAcc())
					fromAccountTypeBox.getItems().add("Student");
				gridPane.add(fromAccountTypeBox, 1,1);
				
				Label payAmt = new Label("Pay Amount :");
				gridPane.add(payAmt, 0, 2);

				final TextField withdrawAmtField = new TextField();
				withdrawAmtField.setPrefHeight(40);
				gridPane.add(withdrawAmtField, 1, 2);
				
				Button payBtn = new Button("Pay");
				payBtn.setPrefHeight(40);
				payBtn.setDefaultButton(true);
				payBtn.setPrefWidth(100);
				gridPane.add(payBtn, 0, 3, 2, 1);
				GridPane.setHalignment(payBtn, HPos.CENTER);
				GridPane.setMargin(payBtn, new Insets(20, 0, 20, 0));

				Scene secondScene = new Scene(gridPane, 500, 300);

				// New window (Stage)
				final Stage newWindow = new Stage();
				newWindow.setTitle("Pay Hydro Bill");
				newWindow.setScene(secondScene);

				// Set position of second window, related to primary window.
				newWindow.setX(loginManager.primaryStage.getX() + 100);
				newWindow.setY(loginManager.primaryStage.getY() + 100);

				newWindow.show();

				payBtn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						if (fromAccountTypeBox.getValue()==null || withdrawAmtField.getText().isEmpty()) {
							showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), "Inputs Missing",
									"Please Enter values in all the Fields!!");
						} else {
							int index = CustomerReadWriteData.customerDetailsList.indexOf(customerDetails);
							ErrorDetails error = CustomerReadWriteData.payUtilitiesBill(customerDetails, withdrawAmtField.getText(), fromAccountTypeBox.getValue());
							if (error != null) {
								showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), error.getErrorMessage(),
										error.getErrorDescription());
							} else {
								newWindow.close();
								showAlert(AlertType.CONFIRMATION, loginManager.getScene().getWindow(),
										"Pay For Utilties", "Bill paid Successfully!!");
								CustomerDetails updatedCustInfo = CustomerReadWriteData.customerDetailsList.get(index);
								loginManager.showExistingCustTransactionView(updatedCustInfo);
							}
						}
					}
				});
			}
		});
		
		phoneBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GridPane gridPane = createRegistrationFormPane();

				Label payFromAcct = new Label("Pay from :");
				gridPane.add(payFromAcct, 0, 1);
				
				final ComboBox<String> fromAccountTypeBox = new ComboBox<String>();
				if (customerDetails.isChequingAcc())
					fromAccountTypeBox.getItems().add("Chequing");
				if (customerDetails.isSavingsAcc())
					fromAccountTypeBox.getItems().add("Savings");
				if (customerDetails.isStudentAcc())
					fromAccountTypeBox.getItems().add("Student");
				gridPane.add(fromAccountTypeBox, 1,1);
				
				Label payAmt = new Label("Pay Amount :");
				gridPane.add(payAmt, 0, 2);

				final TextField withdrawAmtField = new TextField();
				withdrawAmtField.setPrefHeight(40);
				gridPane.add(withdrawAmtField, 1, 2);
				
				Label networkProvider = new Label("Network Provider :");
				gridPane.add(networkProvider, 0, 3);
				
				final ComboBox<String> networkProviderBox = new ComboBox<String>();
				networkProviderBox.getItems().add("Rogers");
				networkProviderBox.getItems().add("Bell");
				networkProviderBox.getItems().add("Virgin");
				networkProviderBox.getItems().add("Chattr");
				networkProviderBox.getItems().add("Fido");
				networkProviderBox.getItems().add("Freedom");
				gridPane.add(networkProviderBox, 1,3);
				
				Button payBtn = new Button("Pay");
				payBtn.setPrefHeight(40);
				payBtn.setDefaultButton(true);
				payBtn.setPrefWidth(100);
				gridPane.add(payBtn, 0, 4, 2, 1);
				GridPane.setHalignment(payBtn, HPos.CENTER);
				GridPane.setMargin(payBtn, new Insets(20, 0, 20, 0));

				Scene secondScene = new Scene(gridPane, 500, 300);

				// New window (Stage)
				final Stage newWindow = new Stage();
				newWindow.setTitle("Pay Phone Bill");
				newWindow.setScene(secondScene);

				// Set position of second window, related to primary window.
				newWindow.setX(loginManager.primaryStage.getX() + 100);
				newWindow.setY(loginManager.primaryStage.getY() + 100);

				newWindow.show();

				payBtn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						if (fromAccountTypeBox.getValue()==null || withdrawAmtField.getText().isEmpty() ||
								networkProviderBox.getValue()==null) {
							showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), "Inputs Missing",
									"Please Enter values in all the Fields!!");
						} else {
							int index = CustomerReadWriteData.customerDetailsList.indexOf(customerDetails);
							ErrorDetails error = CustomerReadWriteData.payUtilitiesBill(customerDetails, withdrawAmtField.getText(), fromAccountTypeBox.getValue());
							if (error != null) {
								showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), error.getErrorMessage(),
										error.getErrorDescription());
							} else {
								newWindow.close();
								showAlert(AlertType.CONFIRMATION, loginManager.getScene().getWindow(),
										"Pay For Utilties", "Bill paid Successfully!!");
								CustomerDetails updatedCustInfo = CustomerReadWriteData.customerDetailsList.get(index);
								loginManager.showExistingCustTransactionView(updatedCustInfo);
							}
						}
					}
				});
			}
		});

		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ErrorDetails errorDetails = null;
				if (!creditCrdNum.getText().isEmpty() && !mothMaidenName.getText().isEmpty()) {
					errorDetails = CustomerReadWriteData.activateCreditCard(creditCrdNum.getText(),
							mothMaidenName.getText());
					if (errorDetails != null) {
						showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), errorDetails.getErrorMessage(),
								errorDetails.getErrorDescription());
					} else {
						showAlert(AlertType.CONFIRMATION, loginManager.getScene().getWindow(), "Credit Card",
								"Credit card activated succesfully!!");
					}
				} else {
					showAlert(AlertType.ERROR, loginManager.getScene().getWindow(), "No Inputs Found",
							"Please Enter the inputs!!!");
				}
			}
		});

		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				creditCrdNum.setText("");
				mothMaidenName.setText("");
			}
		});
	}
}
