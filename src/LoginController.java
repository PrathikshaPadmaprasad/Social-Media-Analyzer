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
		UserModel user=new UserModel();
		
		String username = usernameTextFiled.getText();
		String password = PasswordTextFiled.getText();
		
		if(username.isEmpty()||password.isEmpty()) {
			MessageLabel.setText("No blank fields are allowed");
			return;
		}
		
		
		boolean isUserLoggedIn = user.login(username, password);

		if (isUserLoggedIn) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDashboard.fxml"));
			
			Parent userDashboardParent = loader.load();
			UserDashboardController userDashboardController = loader.getController();
			userDashboardController.displayMessage();
			userDashboardController.displayUserDetails();
			userDashboardController.populateAllPosts();

			Scene userScene = new Scene(userDashboardParent);
			Stage stage = (Stage) LoginButton.getScene().getWindow();

			stage.setScene(userScene);
			stage.show();

		}else {
		    MessageLabel.setText("User is not registered");
		}
	
	}
}
