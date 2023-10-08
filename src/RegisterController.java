import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

	@FXML
	private Button cancelButton;

	@FXML
	private Label registerMessageLabel;

	@FXML
	private TextField usernameTextFiled;

	@FXML
	private TextField PasswordTextFiled;

	@FXML
	private TextField FirstNameTextField;

	@FXML
	private TextField LastNameTextField;
	@FXML
	private Button registerloginbutton;
	
	private Connection connectDB;
	
	public RegisterController() {
		this.connectDB=ApplicationModel.getInstance().getDatabaseConnection();
	}

	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void registerButtonOnAction(ActionEvent e) {
		registerUser();

	}

	public void registerloginbuttonOnAction(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
			Parent LoginSceneParent = loader.load();
			LoginController login = loader.getController();
			
			Scene LoginScene = new Scene(LoginSceneParent);

			// Get the stage information
			Stage stage = (Stage) registerloginbutton.getScene().getWindow();

			// Switch scene
			stage.setScene(LoginScene);
			stage.show();

		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Error loading Register.fxml: " + e1.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("An unexpected error occurred: " + e1.getMessage());
		}
	}

		
	

	public void registerUser() {
		String username = usernameTextFiled.getText().trim();
		String password = PasswordTextFiled.getText().trim();
		String firstName = FirstNameTextField.getText().trim();
		String lastName = LastNameTextField.getText().trim();

		// Check if any field is blank
		if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
			registerMessageLabel.setText("No Blank fields are allowed");
			// Display an error message to the user (you can use an alert or a label for
			// this)
			return;
		}
		
		UserModel userModel = new UserModel();
		
		// Check if the username already exists in the database
		
		if (userModel.isUserRegisteredAlready(username)) {
//			            System.out.println("This username is already taken.");
			registerMessageLabel.setText("This username is already registered");
			// Display an error message to the user (you can use an alert or a label for
			// this)
			return;
		}
		
		int newUserId = userModel.getNewRegistrationUserId();
		User user = new User(newUserId, username, password, firstName, lastName);
		if (userModel.register(user)) {
			registerMessageLabel.setText("User Registered sucesfully!");
			
		}
		else {
			registerMessageLabel.setText("User Registeration failed!");
		}
	}
	

}
