package controller;
import java.io.IOException;
import java.sql.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Post;

public class DisplayPostController {
	
	@FXML
	private Label PostidLabel;
	@FXML
	private Label AuthorLabel;
	@FXML
	private Label ContentLabel;
	
	@FXML
	private Label LikesLabel;
	
	@FXML
	private Label SharesLabel;
	@FXML
	private Button backButton;
	@FXML
	private Label datetimeLabel;


//Functions to display the post contents 
public void setdetails(Post newSelection) {
	
	PostidLabel.setText(String.valueOf(newSelection.getId()));
	AuthorLabel.setText(newSelection.getAuthor());
	ContentLabel.setText(newSelection.getContent());
	LikesLabel.setText(String.valueOf(newSelection.getLikes()));
	SharesLabel.setText(String.valueOf(newSelection.getShares()));
	datetimeLabel.setText(String.valueOf(newSelection.getDateTime()));
}

//Function to get back to user dashboard scene
public void backbuttonOnAction(ActionEvent e) {
	try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserDashboard.fxml"));
		Parent UserDashboardParent = loader.load();
		UserDashboardController Controller = loader.getController();

		
		Controller.displayMessage();
		Controller.displayUserDetails();
		Controller.populateAllPosts();
		
		Scene UserDashboardScene = new Scene(UserDashboardParent);
		Stage stage = (Stage) backButton.getScene().getWindow();

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
