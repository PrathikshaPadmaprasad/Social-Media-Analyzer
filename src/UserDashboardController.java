//import javafx.fxml.FXML;
//import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UserDashboardController {

	@FXML
	private Label LoginMessegaeLabel;
	@FXML
	private Label UserNameLabel;
	@FXML
	private Label PasswordLabel;
	@FXML
	private Label FirstNameLabel;
	@FXML
	private Label LastNameLabel;
	@FXML
	private Button EditButton;

	@FXML
	private Button CreateButton;

	private int loggedInUserId;
	
	private Connection connectDB;
	
	@FXML
	private TableView<Post> tableview;
	@FXML
	private TableColumn<Post,Integer> postIdcolumn;
	@FXML
	private TableColumn<Post,String> Authorcolumn;
	
	
	public void displayMessage(String username) {

		LoginMessegaeLabel.setText("Hello " + username + ", Welcome to the dashboard");

	}

	public void displayUserDetails(String username, String Password, String FirstName, String LastName) {
		UserNameLabel.setText(username);
		PasswordLabel.setText(Password);
		FirstNameLabel.setText(FirstName);
		LastNameLabel.setText(LastName);
	}

	public void EditButtonOnAction(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EditScene.fxml"));
			Parent EditSceneParent = loader.load();
			EditSceneController esc = loader.getController();
			esc.setDatabaseConnection(connectDB);
			esc.setLoginUserId(loggedInUserId);
			Scene EditScene = new Scene(EditSceneParent);

			// Get the stage information
			Stage stage = (Stage) EditButton.getScene().getWindow();

			// Switch scene
			stage.setScene(EditScene);
			stage.show();

		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Error loading Register.fxml: " + e1.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("An unexpected error occurred: " + e1.getMessage());
		}
	}

	public void setLoginUserId(int userId) {
		this.loggedInUserId = userId;
	}

	public void setDatabaseConnection(Connection connectDB) {
		this.connectDB = connectDB;
	}
	
	public void CreateButtonOnAction(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateScene.fxml"));
			Parent createSceneParent = loader.load();
			CreateController createController = loader.getController();

			
			createController.setDatabaseConnection(connectDB);
			createController.setLoginUserId(loggedInUserId);
			
			Scene createScene = new Scene(createSceneParent);
			Stage stage = (Stage) CreateButton.getScene().getWindow();

			stage.setScene(createScene);
			stage.show();

		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Error loading Register.fxml: " + e1.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("An unexpected error occurred: " + e1.getMessage());
		}

		
	}
	
	public void populatePosts() throws SQLException {
		PostModel postModel = new PostModel(connectDB);
		List<Post> posts = postModel.getAllPosts();
		ObservableList<Post> observablelist = FXCollections.observableArrayList(posts);
		
		postIdcolumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
		Authorcolumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
		tableview.setItems(observablelist);
		
		
		tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            // Handle the selected item
	            handleItemSelected(newSelection);
	        }
	    });
	}

	private void handleItemSelected(Post newSelection) {
		
		System.out.println(newSelection.getId());
		
	
		
	}
	
}
