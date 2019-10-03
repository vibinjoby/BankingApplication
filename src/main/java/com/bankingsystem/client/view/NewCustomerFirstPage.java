package com.bankingsystem.client.view;

import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.CustomerName;
import com.bankingsystem.util.FrontEndUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * https://www.callicoder.com/javafx-registration-form-gui-tutorial/
 *
 */
public class NewCustomerFirstPage {

	public static Stage primaryStage;

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

	public static void addUIControls(final GridPane gridPane,final LoginManager loginManager) {
		// Add Header
		Label headerLabel = new Label("NEW CUSTOMER");
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		gridPane.add(headerLabel, 0, 0, 2, 1);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

		// Add Name Label
		Label firstNameLabel = new Label("First Name : ");
		gridPane.add(firstNameLabel, 0, 1);

		// Add Name Text Field
		final TextField firstNameField = new TextField();
		firstNameField.setPrefHeight(40);
		gridPane.add(firstNameField, 1, 1);

		// Add Name Label
		Label middleNameLabel = new Label("Middle Name : ");
		gridPane.add(middleNameLabel, 0, 2);

		// Add Name Text Field
		final TextField middleNameField = new TextField();
		middleNameField.setPrefHeight(40);
		gridPane.add(middleNameField, 1, 2);

		// Add Name Label
		Label lastNameLabel = new Label("Last Name : ");
		gridPane.add(lastNameLabel, 0, 3);

		// Add Name Text Field
		final TextField lastNameField = new TextField();
		lastNameField.setPrefHeight(40);
		gridPane.add(lastNameField, 1, 3);

		// Add Email Label
		Label emailLabel = new Label("Email ID : ");
		gridPane.add(emailLabel, 0, 4);

		// Add Email Text Field
		final TextField emailField = new TextField();
		emailField.setPrefHeight(40);
		gridPane.add(emailField, 1, 4);

		// Add Next Button
		Button nextButton = new Button("Next >");
		nextButton.setPrefHeight(40);
		nextButton.setDefaultButton(true);
		nextButton.setPrefWidth(100);
		gridPane.add(nextButton, 0, 6, 2, 1);
		GridPane.setHalignment(nextButton, HPos.CENTER);
		GridPane.setMargin(nextButton, new Insets(20, 0, 20, 0));

		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if (firstNameField.getText().isEmpty()) {
					FrontEndUtils.checkMandatoryFields(firstNameField, gridPane, "First Name");
				} else if (middleNameField.getText().isEmpty()) {
					FrontEndUtils.checkMandatoryFields(middleNameField, gridPane, "Middle Name");
				} else if (lastNameField.getText().isEmpty()) {
					FrontEndUtils.checkMandatoryFields(lastNameField, gridPane, "Last Name");
				} else if (emailField.getText().isEmpty()) {
					FrontEndUtils.checkMandatoryFields(emailField, gridPane, "Email Field");
				} else {
					CustomerDetails custDetails = new CustomerDetails(null, null, null, emailField.getText(), null, null, null, null,
							false, false, false);
					custDetails.setName(new CustomerName(firstNameField.getText(), middleNameField.getText(),
							lastNameField.getText()));
					primaryStage.hide();
					GridPane secondGrid = NewCustomerSecondPage.createRegistrationFormPane();
					NewCustomerSecondPage.addUIControls(secondGrid, custDetails,loginManager,primaryStage);
					Scene scene = new Scene(secondGrid, 800, 500);
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			}
		});

	}
}