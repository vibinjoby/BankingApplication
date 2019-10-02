package application;

import com.bankingsystem.db.CustomerReadWriteData;
import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.PersonalDetails;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SecondPage {

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
		ColumnConstraints columnOneConstraints = new ColumnConstraints(200, 100, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.RIGHT);

		// columnTwoConstraints will be applied to all the nodes placed in column two.
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(50, 50, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);

		gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

		return gridPane;
	}

	public static void addUIControls(final GridPane gridPane, final CustomerDetails custDetails) {
		// Add Header
		Label headerLabel = new Label("Enter the Personal Details");
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		gridPane.add(headerLabel, 0, 0, 2, 1);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

		// Add Name Label
		Label accTypeLbl = new Label("Account Type : ");
		gridPane.add(accTypeLbl, 0, 1);

		// Add Name Text Field
		final CheckBox checkBoxSav = new CheckBox("Savings");
		checkBoxSav.setPrefHeight(40);
		gridPane.add(checkBoxSav, 1, 1);

		final CheckBox checkBoxCheq = new CheckBox("Chequing");
		checkBoxCheq.setPrefHeight(40);
		gridPane.add(checkBoxCheq, 2, 1);

		final CheckBox checkBoxStud = new CheckBox("Student");
		checkBoxStud.setPrefHeight(40);
		gridPane.add(checkBoxStud, 3, 1);

		// Add Name Label
		Label maideNameLbl = new Label("Mother's maiden Name :");
		gridPane.add(maideNameLbl, 0, 2);

		// Add Name Text Field
		final TextField maideNameField = new TextField();
		maideNameField.setPrefHeight(40);
		gridPane.add(maideNameField, 1, 2);

		// Add Name Label
		Label sinNumLbl = new Label("SIN Number : ");
		gridPane.add(sinNumLbl, 0, 3);

		// Add Name Text Field
		final TextField sinNumField = new TextField();
		sinNumField.setPrefHeight(40);
		gridPane.add(sinNumField, 1, 3);

		// Add Email Label
		Label pinNumLbl = new Label("PIN Number : ");
		gridPane.add(pinNumLbl, 0, 4);

		// Add Email Text Field
		final TextField pinNumField = new TextField();
		pinNumField.setPrefHeight(40);
		gridPane.add(pinNumField, 1, 4);

		// Add Next Button
		Button nextButton = new Button("Submit");
		nextButton.setPrefHeight(40);
		nextButton.setDefaultButton(true);
		nextButton.setPrefWidth(100);
		gridPane.add(nextButton, 0, 6, 2, 1);
		GridPane.setHalignment(nextButton, HPos.CENTER);
		GridPane.setMargin(nextButton, new Insets(20, 0, 20, 0));

		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				RegistrationFormApplication.primaryStage.hide();
				custDetails.setPersonalDetails(
						new PersonalDetails(maideNameField.getText(), sinNumField.getText(), pinNumField.getText()));
				custDetails.setAccountNumber(String.valueOf(UiUtilities.generateAccountNumber()));
				custDetails.setCustomerId(String.valueOf(UiUtilities.generateCustomerId()));
				if (CustomerReadWriteData.addNewCustomer(custDetails))
					UiUtilities.showAlert(AlertType.INFORMATION, gridPane.getScene().getWindow(),
							"Registration Successful! ", "\n Welcome " + custDetails.getName().getFirstName()
									+ "Your Account Number is: " + custDetails.getAccountNumber());
				else
					UiUtilities.showAlert(AlertType.ERROR, gridPane.getScene().getWindow(),
							"Registration Failed!!", "\n Please try again Later");
			}
		});
	}
}
