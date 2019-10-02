package application;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.bankingsystem.model.ApplicationProperties;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class UiUtilities {
	
	private static int custId;
	private static long accNumber;
	private static long debitCardStartNumber;
	private static long creditCardStartNumber;
	static {
		try {
			readAppProperties();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void checkMandatoryFields(TextField textField,GridPane gridPane) {
		if(textField.getText().isEmpty()) {
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
	
	
	public static void readAppProperties() throws FileNotFoundException {
		JsonReader reader = new JsonReader(new FileReader("/application.json"));
		Gson gson = new Gson();
		ApplicationProperties appProps= gson.fromJson(reader, ApplicationProperties.class);
		accNumber = Long.parseLong(appProps.getAccNumber());
		custId = Integer.parseInt(appProps.getCustomerId());
		debitCardStartNumber = Long.parseLong(appProps.getDebitCardStartNumber());
		creditCardStartNumber = Long.parseLong(appProps.getCreditCardStartNumber());
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
