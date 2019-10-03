package com.bankingsystem.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.bankingsystem.model.ApplicationProperties;
import com.bankingsystem.model.CardDetails;
import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.PersonalDetails;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class FrontEndUtils extends BackEndUtils {

	private static int custId;
	private static long accNumber;
	private static long debitCardStartNumber;
	private static long creditCardStartNumber;
	
	static {
		try {
			readAppProperties();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void checkMandatoryFields(TextField textField, GridPane gridPane,String message) {
		if (textField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter the "+message);
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

	public static void readAppProperties() throws IOException {
		File file = new File(FrontEndUtils.class.getClassLoader().getResource("application.json").getFile());
		JsonReader reader = new JsonReader(new FileReader(file.getAbsolutePath()));
		Gson gson = new Gson();
		ApplicationProperties appProps = gson.fromJson(reader, ApplicationProperties.class);
		accNumber = Long.parseLong(appProps.getAccNumber());
		custId = Integer.parseInt(appProps.getCustomerId());
		debitCardStartNumber = Long.parseLong(appProps.getDebitCardStartNumber());
		creditCardStartNumber = Long.parseLong(appProps.getCreditCardStartNumber());
		updateAppProps(appProps);
	}

	public static void updateAppProps(ApplicationProperties appProps) throws IOException {
		appProps.setAccNumber(String.valueOf(accNumber+1));
		appProps.setDebitCardStartNumber(String.valueOf(debitCardStartNumber+1));
		appProps.setCreditCardStartNumber(String.valueOf(creditCardStartNumber+1));
		appProps.setCustomerId(String.valueOf(custId+1));
		String json = getJsonObjAsString(appProps);
		Files.write(Paths.get(new File(FrontEndUtils.class.getClassLoader().getResource("application.json").getFile())
				.getAbsolutePath()), toPrettyFormat(json).getBytes());

	}
	
	public static void addCustDetails(final CustomerDetails custDetails, final CheckBox checkBoxSav,
			final CheckBox checkBoxCheq, final CheckBox checkBoxStud, final TextField maideNameField,
			final TextField sinNumField, final TextField pinNumField) {
		if (checkBoxCheq.isSelected())
			custDetails.setChequingAcc(true);
		if (checkBoxSav.isSelected())
			custDetails.setSavingsAcc(true);
		if (checkBoxStud.isSelected())
			custDetails.setStudentAcc(true);

		custDetails.setPersonalDetails(
				new PersonalDetails(maideNameField.getText(), sinNumField.getText(), pinNumField.getText()));
		custDetails.setAccountNumber(String.valueOf(FrontEndUtils.generateAccountNumber()));
		custDetails.setCustomerId(String.valueOf(FrontEndUtils.generateCustomerId()));
		custDetails.setDebitCardDetails(
				new CardDetails(String.valueOf(FrontEndUtils.generateDebitCardNumber()), "08/21", "123", true));
		custDetails.setCreditCardDetails(new CardDetails(
				String.valueOf(FrontEndUtils.generateCreditCardNumber()), "07/21", "467", false));
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
