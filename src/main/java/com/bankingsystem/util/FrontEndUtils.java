package com.bankingsystem.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.bankingsystem.model.AccountDetails;
import com.bankingsystem.model.AccountType;
import com.bankingsystem.model.ApplicationProperties;
import com.bankingsystem.model.CardDetails;
import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.PersonalDetails;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Window;

public class FrontEndUtils extends BackEndUtils {

	private static int custId;
	private static long accNumber;
	private static long debitCardStartNumber;
	private static long creditCardStartNumber;

	static {
		readAppProperties();
	}

	public static void checkMandatoryFields(TextField textField, GridPane gridPane, String message) {
		if (textField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
					"Please enter the " + message);
			return;
		}
	}

	public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}

	public static void readAppProperties() {
		try {
			File file = new File(FrontEndUtils.class.getClassLoader().getResource("application.json").getFile());
			JsonReader reader = new JsonReader(new FileReader(file.getAbsolutePath()));
			Gson gson = new Gson();
			ApplicationProperties appProps = gson.fromJson(reader, ApplicationProperties.class);
			accNumber = Long.parseLong(appProps.getAccNumber());
			custId = Integer.parseInt(appProps.getCustomerId());
			debitCardStartNumber = Long.parseLong(appProps.getDebitCardStartNumber());
			creditCardStartNumber = Long.parseLong(appProps.getCreditCardStartNumber());
			updateAppProps(appProps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateAppProps(ApplicationProperties appProps) throws IOException {
		appProps.setAccNumber(String.valueOf(accNumber + 1));
		appProps.setDebitCardStartNumber(String.valueOf(debitCardStartNumber + 1));
		appProps.setCreditCardStartNumber(String.valueOf(creditCardStartNumber + 1));
		appProps.setCustomerId(String.valueOf(custId + 1));
		String json = getJsonObjAsString(appProps);
		Files.write(Paths.get(new File(FrontEndUtils.class.getClassLoader().getResource("application.json").getFile())
				.getAbsolutePath()), toPrettyFormat(json).getBytes());
	}

	public static void addCustDetails(final CustomerDetails custDetails, final CheckBox checkBoxSav,
			final CheckBox checkBoxCheq, final CheckBox checkBoxStud, final TextField maideNameField,
			final TextField sinNumField, final TextField pinNumField) {

		AccountDetails chequingAccDetails = null, savingsAccDetails = null, studentAccDetails = null;
		if (checkBoxCheq.isSelected()) {
			custDetails.setChequingAcc(true);
			chequingAccDetails = new AccountDetails("chq" + String.valueOf(accNumber), "100");
		}
		if (checkBoxSav.isSelected()) {
			custDetails.setSavingsAcc(true);
			savingsAccDetails = new AccountDetails("sav" + String.valueOf(accNumber), "100");
		}
		if (checkBoxStud.isSelected()) {
			custDetails.setStudentAcc(true);
			studentAccDetails = new AccountDetails("stud" + String.valueOf(accNumber), "100");
		}
		custDetails.setAccountType(new AccountType(savingsAccDetails, chequingAccDetails, studentAccDetails));

		custDetails.setPersonalDetails(
				new PersonalDetails(maideNameField.getText(), sinNumField.getText(), pinNumField.getText()));
		custDetails.setAccountNumber(String.valueOf(FrontEndUtils.generateAccountNumber()));
		custDetails.setCustomerId(String.valueOf(FrontEndUtils.generateCustomerId()));
		custDetails.setDebitCardDetails(
				new CardDetails(String.valueOf(FrontEndUtils.generateDebitCardNumber()), "08/21", "123", true));
		custDetails.setCreditCardDetails(
				new CardDetails(String.valueOf(FrontEndUtils.generateCreditCardNumber()), "07/21", "467", false));

		readAppProperties();
	}
	public static String getSavingsBalanceDetails(CustomerDetails custDetails) {
		StringBuffer accBalance = new StringBuffer();
		if (custDetails.isSavingsAcc() && custDetails.getAccountType()!=null && custDetails.getAccountType().getSavingsAccount()!=null) {
			accBalance.append("Savings Balance :"+custDetails.getAccountType().getSavingsAccount().getAccountBalance());
		}
		return accBalance.toString();
	}
	public static String getStudentBalanceDetails(CustomerDetails custDetails) {
		StringBuffer accBalance = new StringBuffer();
		if (custDetails.isStudentAcc() && custDetails.getAccountType()!=null && custDetails.getAccountType().getStudentAccount()!=null) {
			accBalance.append("Student Balance :"+custDetails.getAccountType().getStudentAccount().getAccountBalance());
		}
		return accBalance.toString();
	}
	public static String getChequingBalanceDetails(CustomerDetails custDetails) {
		StringBuffer accBalance = new StringBuffer();
		if (custDetails.isChequingAcc() && custDetails.getAccountType()!=null && custDetails.getAccountType().getChequingAccount()!=null) {
			accBalance.append("Chequing Balance :"+custDetails.getAccountType().getChequingAccount().getAccountBalance());
		}
		return accBalance.toString();
	}
	public static GridPane createRegistrationFormPane() {
		// Instantiate a new Grid Pane
		GridPane gridPane = new GridPane();

		// Position the pane at the center of the screen, both vertically and
		// horizontally
		gridPane.setAlignment(Pos.CENTER);

		// Set a padding of 20px on each side
		gridPane.setPadding(new Insets(40, 40, 40, 40));

		// Set the horizontal gap between columns
		gridPane.setHgap(10);

		// Set the vertical gap between rows
		gridPane.setVgap(10);

		// Add Column Constraints

		// columnOneConstraints will be applied to all the nodes placed in column one.
		ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.RIGHT);

		// columnTwoConstraints will be applied to all the nodes placed in column two.
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);

		gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

		return gridPane;
	}
	public static boolean validateSinNumber(String sinNumber) {
		boolean isValid = false;
		if(validateNumber(sinNumber)) {
			if(sinNumber.length() == 10) {
				return true;
			} else {
				return false;
			}
		} else {
			isValid = false;
		}
		return isValid;
	}
	public static boolean validatePinNumber(String pinNumber) {
		boolean isValid = false;
		if(validateNumber(pinNumber)) {
			if(pinNumber.length() ==4) {
				return true;
			} else {
				return false;
			}
		} else {
			isValid = false;
		}
		return isValid;
	}
	/**
	 * @param number
	 * @return
	 */
	public static boolean validateNumber(String number) {
		try {
			 Long.parseLong(number);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static long generateAccountNumber() {
		return accNumber;
	}

	public static long generateDebitCardNumber() {
		return debitCardStartNumber;
	}

	public static long generateCreditCardNumber() {
		return creditCardStartNumber;
	}

	public static long generateCustomerId() {
		return custId;
	}

}
