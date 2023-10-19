package controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import model.PostModel;
import model.User;

public class CreateController {
	
	
@FXML	
private Button CreateButton;
@FXML	
private TextField postidTextField;
@FXML
private TextField contentTextField;
@FXML
private TextField authorTextField;
@FXML
private TextField sharesTextField;
@FXML
private TextField likesTextField;
@FXML
private TextField date_timeTextField;

@FXML
private Button createbackbutton;

@FXML
private Label warningLabel;

private Connection connectDB;

private User user;


public CreateController() {
	this.connectDB=ApplicationModel.getInstance().getDatabaseConnection();
	this.user=ApplicationModel.getInstance().getUser();
}

//Triggers when user wants to create a post and click on create button
public void createButtonOnAction(ActionEvent e) throws SQLException {
	int postid;
	int postshares;
	int postlikes;
	LocalDateTime date_time;
//	check if the postid field is not empty and should be a positive integer
	try {
		if(postidTextField.getText().trim().isEmpty()) {
			warningLabel.setText("Post id  should not be empty");
			return;
		}
		postid = Integer.parseInt(postidTextField.getText().trim());
	 if (postid <= 0) { 
	        warningLabel.setText("Post id should be a positive integer");
	        return; 
	    }
	 	} catch (NumberFormatException e1) {
		warningLabel.setText("Invalid post ID. Post ID should be an integer.");
	    return; 
	}
//	validating content field is not empty and should be a positive integer
	String postcontent = contentTextField.getText().trim();
	if (postcontent.isEmpty()) {
		warningLabel.setText("Post content field cannot be empty");
		return;
	}

//	validating autor is not empty and should be a positive integer
	String postauthor = authorTextField.getText().trim();
	if (postauthor.isEmpty()) {
		warningLabel.setText("Post author field cannot be empty");
		return;
	}
	
//	validating if shares field is not empty and should not be empty and should be a positive integer.
		try {
			if(sharesTextField.getText().trim().isEmpty()) {
				warningLabel.setText("Post shares  should not be empty");
				return;
			}
		postshares = Integer.parseInt(sharesTextField.getText().trim());
		 if (postshares < 0) { 
		        warningLabel.setText("Post shares should be a positive integer");
		        return; 
		    }
		 	} catch (NumberFormatException e1) {
			warningLabel.setText("Invalid number. Post share should be an integer.");
		    return; 
		}
		
	
//		validating if likes field is not empty and should not be empty and should be a positive integer.	
	
	try {
		if(likesTextField.getText().trim().isEmpty()) {
			warningLabel.setText("Post likes  should not be empty");
			return;
		}
	postlikes = Integer.parseInt(likesTextField.getText().trim());
	 if (postlikes < 0) { 
	        warningLabel.setText("Post likes should be a positive integer");
	        return; 
	    }
	 	} catch (NumberFormatException e1) {
		warningLabel.setText("Invalid number. Post likes should be an integer.");
	    return; 
	}
	try {
		 String dateTimeString = date_timeTextField.getText();
		 date_time=LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}catch(DateTimeException e2) {
		warningLabel.setText("Invalid date time format.Please provide the date time format in dd/MM/yyyy HH:mm ");
		return;
	}
	
	
	PostModel postModel=new PostModel();
	
	String outputAfterInserting = postModel.addPost(postid,postcontent,postauthor,postshares,postlikes,date_time);
	warningLabel.setText(outputAfterInserting);
}

//Function to get back to userdashboard from create post scene
public void createbackbuttonOnAction(ActionEvent e) {
	try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserDashboard.fxml"));
		Parent UserDashboardParent = loader.load();
		UserDashboardController Controller = loader.getController();

		
		Controller.displayMessage();
		Controller.displayUserDetails();
		Controller.populateAllPosts();
		
		Scene UserDashboardScene = new Scene(UserDashboardParent);
		Stage stage = (Stage) createbackbutton.getScene().getWindow();

		stage.setScene(UserDashboardScene);
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
	
	
	





