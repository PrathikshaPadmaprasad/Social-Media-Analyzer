import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditSceneController {

	@FXML
	private TextField UsernameTextField;
	@FXML
	private TextField PasswordTextField;
	@FXML
	private TextField FirstNameTextField;
	@FXML
	private TextField LastNameTextField;
	@FXML
	private Button editbackbutton;
	
	@FXML
	private Label updateLabel;

	private User user;

	private Connection connectDB;

	@FXML
	private Button UpdateButton;
	
	public EditSceneController() {
		this.connectDB=ApplicationModel.getInstance().getDatabaseConnection();
		this.user=ApplicationModel.getInstance().getUser();
	}

	public void UpdateButtonOnAction(ActionEvent e) {

		String username = UsernameTextField.getText().trim();
		String password = PasswordTextField.getText().trim();
		String firstName = FirstNameTextField.getText().trim();
		String lastName = LastNameTextField.getText().trim();
		
		if (username.isEmpty()||password.isEmpty()||firstName.isEmpty()||lastName.isEmpty()) {
			updateLabel.setText("No blanks filed allowed");
			return;
		}

		User edituser=new User(this.user.getUserId(), username, password, firstName, lastName);
		UserModel usermodel=new UserModel();
		if(usermodel.userdetailsupdate(edituser)){
			updateLabel.setText("Update successful");

		} else {
			updateLabel.setText("Update unsuccessful");
		}
	}
	public void editbackButtonOnAction(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDashboard.fxml"));
			Parent UserSceneParent = loader.load();
			UserDashboardController udc = loader.getController();
			
			Scene UserScene = new Scene(UserSceneParent);

			// Get the stage information
			Stage stage = (Stage) editbackbutton.getScene().getWindow();

			// SwUserDashboardController userDashboardController = loader.getController();
			udc.displayMessage();
			udc.displayUserDetails();
			udc.populateAllPosts();

			stage.setScene(UserScene);
			stage.show();

		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Error loading Register.fxml: " + e1.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("An unexpected error occurred: " + e1.getMessage());
		}
	}


	}



	

