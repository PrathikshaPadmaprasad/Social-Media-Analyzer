import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private Button SignUpButton;

	@FXML
	private Button LoginButton;

	@FXML
	private TextField usernameTextFiled;

	@FXML
	private TextField PasswordTextFiled;
	@FXML
	private Label MessageLabel;

	private Connection connectDB;
	
	public LoginController() {
		this.connectDB=ApplicationModel.getInstance().getDatabaseConnection();
	}

	

	public void regButtonOnAction(ActionEvent e) throws IOException {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
			Parent registerParent = loader.load();
			RegisterController rc = loader.getController();
		
			Scene registerScene = new Scene(registerParent);

			// Get the stage information
			Stage stage = (Stage) SignUpButton.getScene().getWindow();

			// Switch scene
			stage.setScene(registerScene);
			stage.show();

		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Error loading Register.fxml: " + e1.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("An unexpected error occurred: " + e1.getMessage());
		}
	}

	public void LoginButtonOnAction(ActionEvent e) throws IOException, SQLException {

		String username = usernameTextFiled.getText();
		String password = PasswordTextFiled.getText();
		
		ResultSet rs = isUserRegistered(username, password);

		if (rs.next()) {
			login(rs);
		}
	}

	public void login(ResultSet rs) {
		try {
			int userid = rs.getInt("userId");
			String username = rs.getString("UserName");
			String password = rs.getString("Password");
			String firstname = rs.getString("FirstName");
			String lastname = rs.getString("LastName");
			
			User user=new User(userid,username,password,firstname,lastname);
			ApplicationModel.getInstance().setUser(user);
			
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDashboard.fxml"));
			
			Parent userDashboardParent = loader.load();
			UserDashboardController userDashboardController = loader.getController();
			userDashboardController.displayMessage();
			userDashboardController.displayUserDetails();
			userDashboardController.populatePosts();

			Scene userScene = new Scene(userDashboardParent);
			Stage stage = (Stage) LoginButton.getScene().getWindow();

			stage.setScene(userScene);
			stage.show();

		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Error loading Register.fxml: " + e1.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("An unexpected error occurred: " + e1.getMessage());
		}

	}

	public ResultSet isUserRegistered(String username, String password) {
		try {

			// Check if any field is blank
			if (username.isEmpty() || password.isEmpty()) {
				MessageLabel.setText("No Blank fields are allowed");
			}
			// Create the SQL query to check if the username exists
			String query = "SELECT * FROM users WHERE Username ='" + username + "' and Password='" + password + "'";
//	        String query = "SELECT * FROM users WHERE Username ='"+ username + "' and Password ='"+password+"'";

			Statement statement = connectDB.createStatement();

			// Execute the query
			ResultSet resultSet = statement.executeQuery(query);

			// Check if the result set contains any rows
			return resultSet;

			// Returns true if the user is already registered

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// Handle the exception according to your application's requirements
			return null; // Assume an error means the user is not registered
		}
	}
}
