//import javafx.fxml.FXML;
//import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
	private Label LoginMessageLabel;
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
	@FXML
	private TextField searchtextField;

	
	
	private Connection connectDB;
	
	private User user;
	
	@FXML
	private TableView<Post> tableview;
	@FXML
	private TableColumn<Post,Integer> postIdcolumn;
	@FXML
	private TableColumn<Post,String> Authorcolumn;
	
	public UserDashboardController() {
		this.connectDB=ApplicationModel.getInstance().getDatabaseConnection();
		this.user = ApplicationModel.getInstance().getUser();
	}
	
	public void displayMessage() {

		LoginMessageLabel.setText("Hello "+ user.getFirstName()+ " " +user.getLastName() + ", Welcome to the dashboard");

	}

	public void displayUserDetails() {
		UserNameLabel.setText(this.user.getUserName());
		PasswordLabel.setText(user.getPassword());
		FirstNameLabel.setText(user.getFirstName());
		LastNameLabel.setText(user.getLastName());
	}

	public void EditButtonOnAction(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EditScene.fxml"));
			Parent EditSceneParent = loader.load();
			EditSceneController esc = loader.getController();
			
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



	public void CreateButtonOnAction(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateScene.fxml"));
			Parent createSceneParent = loader.load();
			CreateController createController = loader.getController();

			
			

			
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
		PostModel postModel = new PostModel();
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
		
		try {
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("DisplayPost.fxml"));
		    Parent displayPostParent = loader.load();
		    DisplayPostController dsp = loader.getController();
		    dsp.setdetails(newSelection);
		    Scene displayPostScene = new Scene(displayPostParent);

		    // Get the stage information
		    Stage stage = (Stage) tableview.getScene().getWindow();

		    // Switch scene
		    stage.setScene(displayPostScene);
		    stage.show();

		} catch (IOException e1) {
		    e1.printStackTrace();
		    System.err.println("Error loading DisplayPost.fxml: " + e1.getMessage());
		} catch (Exception e1) {
		    e1.printStackTrace();
		    System.err.println("An unexpected error occurred: " + e1.getMessage());
		}

	}
	public void serachButtonOnAction(ActionEvent e) {
		PostModel postModel=new PostModel();
		List<Post> postsList = new ArrayList<>();
		int searchid=Integer.parseInt(searchtextField.getText());
		try {
			Post post = postModel.searchbyId(searchid);
			System.out.println(post);
			postsList.add(post);
			ObservableList<Post> observablelist = FXCollections.observableArrayList(postsList);
			postIdcolumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
			Authorcolumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
			tableview.setItems(observablelist);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	
	
		
	}
	
