package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.bankingsystem.model.ApplicationProperties;
import com.bankingsystem.util.BankingUtil;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class FrontEndUtils extends BankingUtil {

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

	public static void checkMandatoryFields(TextField textField, GridPane gridPane) {
		if (textField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your name");
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
