package controller;
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
import model.ApplicationModel;
import model.NonVipUser;
import model.User;
import model.UserModel;
import model.VipUser;

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

//	Function to update the details of the users
	public void UpdateButtonOnAction(ActionEvent e) {

		String username = UsernameTextField.getText().trim();
		String password = PasswordTextField.getText().trim();
		String firstName = FirstNameTextField.getText().trim();
		String lastName = LastNameTextField.getText().trim();
		
//		validating username,password,firstname,lastname is not empty
		if (username.isEmpty()||password.isEmpty()||firstName.isEmpty()||lastName.isEmpty()) {
			updateLabel.setText("No blanks filed allowed");
			return;
		}
		
//validating if the user is Vip or Non Vip user
		User editUser;
		if (user instanceof VipUser) {
			editUser = new VipUser(this.user.getUserId(), username, password, firstName, lastName);
		}
		else {
			editUser =new NonVipUser(this.user.getUserId(), username, password, firstName, lastName);
		}
		
		UserModel usermodel=new UserModel();
		if(usermodel.userdetailsupdate(editUser)){
			updateLabel.setText("Update successful");

		} else {
			updateLabel.setText("Update unsuccessful");
		}
	}
	
//Function to go back to userdashboard from update scene
	public void editbackButtonOnAction(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserDashboard.fxml"));
			Parent UserSceneParent = loader.load();
			UserDashboardController udc = loader.getController();
			
			Scene UserScene = new Scene(UserSceneParent);

			
			Stage stage = (Stage) editbackbutton.getScene().getWindow();

		
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



	

