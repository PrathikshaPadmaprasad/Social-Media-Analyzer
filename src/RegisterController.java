import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
	
	private Connection connectDB;

	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void registerButtonOnAction(ActionEvent e) {
		registerUser();

	}

	public void displayMessage(String username) {

		registerMessageLabel.setText("Hello " + username + ", Welcome to the dashboard");

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
		try {
			
			// Check if the username already exists in the database
			String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
			PreparedStatement checkUserStatement = connectDB.prepareStatement(checkUserQuery);
			checkUserStatement.setString(1, username);
			ResultSet resultSet = checkUserStatement.executeQuery();
			resultSet.next();
			int userCount = resultSet.getInt(1);

			if (userCount > 0) {
//			            System.out.println("This username is already taken.");
				registerMessageLabel.setText("This username is already registered");
				// Display an error message to the user (you can use an alert or a label for
				// this)
				return;
			}
			String countquery = "SELECT COUNT(*) AS row_count FROM users";
			PreparedStatement countStatement = connectDB.prepareStatement(countquery);
			ResultSet r = countStatement.executeQuery();
			int row = 0;
			if (r.next()) {
				row = r.getInt("row_count");
				row++;
			}

			// Insert the user into the database

			String insertUserQuery = "INSERT INTO Users (userId,UserName, Password, FirstName, LastName) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement insertUserStatement = connectDB.prepareStatement(insertUserQuery);
			insertUserStatement.setInt(1, row);
			insertUserStatement.setString(2, username);
			insertUserStatement.setString(3, password);
			insertUserStatement.setString(4, firstName);
			insertUserStatement.setString(5, lastName);
			insertUserStatement.executeUpdate();

			// Display a success message to the user (you can use an alert or a label for
			// this)
			registerMessageLabel.setText("User Registered sucesfully!");
			

		} catch (SQLException e) {
			System.out.println("An error occurred during registration: " + e.getMessage());
			// Display an error message to the user (you can use an alert or a label for
			// this)
		}
	}
	
	public void setDatabaseConnection(Connection connectDB) {
		this.connectDB = connectDB;
	}
}
