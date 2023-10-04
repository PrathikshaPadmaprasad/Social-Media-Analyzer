import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EditSceneController {

	@FXML
	private TextField UsernameTextField;
	@FXML
	private TextField PasswordTextField;
	@FXML
	private TextField FirstNameTextField;
	@FXML
	private TextField LastNameTextField;

	private int loggedInUserId;

	private Connection connectDB;

	@FXML
	private Button UpdateButton;

	public void UpdateButtonOnAction(ActionEvent e) {

		String username = UsernameTextField.getText().trim();
		String password = PasswordTextField.getText().trim();
		String firstName = FirstNameTextField.getText().trim();
		String lastName = LastNameTextField.getText().trim();

		try {

			// Check if the username already exists in the database
			String UpdateQuery = "Update Users Set UserName='" + UsernameTextField.getText() + "',Password='"
					+ PasswordTextField.getText() + "',FirstName='" + FirstNameTextField.getText() + "',LastName='"
					+ LastNameTextField.getText() + "' Where userId=" + loggedInUserId;
			PreparedStatement checkUserStatement = connectDB.prepareStatement(UpdateQuery);
			
			checkUserStatement.executeUpdate();
			System.out.println("Update successful");

		} catch (SQLException e1) {
			System.out.println("An error occurred during registration: " + e1.getMessage());
			// Display an error message to the user (you can use an alert or a label for
			// this)
		}
	}

	public void setLoginUserId(int userId) {
		this.loggedInUserId = userId;
	}

	public void setDatabaseConnection(Connection connectDB) {
		this.connectDB = connectDB;
	}
}
