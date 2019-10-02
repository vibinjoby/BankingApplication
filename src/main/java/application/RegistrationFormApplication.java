package application;
import com.bankingsystem.model.CustomerDetails;
import com.bankingsystem.model.CustomerName;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
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
public class RegistrationFormApplication extends Application {

	static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Banking Application System");

        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 800, 500);
        // Set the scene in primary stage	
        primaryStage.setScene(scene);
        RegistrationFormApplication.primaryStage = primaryStage;
        
        primaryStage.show();
    }


    private GridPane createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
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
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIControls(final GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("New Customer");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // Add Name Label
        Label firstNameLabel = new Label("First Name : ");
        gridPane.add(firstNameLabel, 0,1);

        // Add Name Text Field
        final TextField firstNameField = new TextField();
        firstNameField.setPrefHeight(40);
        gridPane.add(firstNameField, 1,1);
        
        // Add Name Label
        Label middleNameLabel = new Label("Middle Name :");
        gridPane.add(middleNameLabel, 0,2);

        // Add Name Text Field
        final TextField middleNameField = new TextField();
        middleNameField.setPrefHeight(40);
        gridPane.add(middleNameField, 1,2);
        
        // Add Name Label
        Label lastNameLabel = new Label("Last Name : ");
        gridPane.add(lastNameLabel, 0,3);

        // Add Name Text Field
        final TextField lastNameField = new TextField();
        lastNameField.setPrefHeight(40);
        gridPane.add(lastNameField, 1,3);


        // Add Email Label
        Label emailLabel = new Label("Email ID : ");
        gridPane.add(emailLabel, 0, 4);

        // Add Email Text Field
        final TextField emailField = new TextField();
        emailField.setPrefHeight(40);
        gridPane.add(emailField, 1, 4);

        // Add Next Button
        Button nextButton = new Button("Next>");
        nextButton.setPrefHeight(40);
        nextButton.setDefaultButton(true);
        nextButton.setPrefWidth(100);
        gridPane.add(nextButton, 0, 6, 2, 1);
        GridPane.setHalignment(nextButton, HPos.CENTER);
        GridPane.setMargin(nextButton, new Insets(20, 0,20,0));

        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
				/*
				 * if(firstNameField.getText().isEmpty()) {
				 * UiUtilities.checkMandatoryFields(firstNameField, gridPane); } else
				 * if(middleNameField.getText().isEmpty()) {
				 * UiUtilities.checkMandatoryFields(middleNameField, gridPane); } else
				 * if(lastNameField.getText().isEmpty()) {
				 * UiUtilities.checkMandatoryFields(lastNameField, gridPane); } else
				 * if(emailField.getText().isEmpty()) {
				 * UiUtilities.checkMandatoryFields(emailField, gridPane); }
				 */
                CustomerDetails custDetails = new CustomerDetails(null, null, null, null, null, null, null, null, false, false, false);
                custDetails.setName(new CustomerName(firstNameField.getText(), middleNameField.getText(), lastNameField.getText()));
                primaryStage.hide();
                GridPane secondGrid = SecondPage.createRegistrationFormPane();
                SecondPage.addUIControls(secondGrid,custDetails);
                Scene scene = new Scene(secondGrid, 800, 500);
                // Set the scene in primary stage	
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        });
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}