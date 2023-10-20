//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ApplicationModel;
import model.Post;
import model.PostModel;
import model.User;
import model.UserModel;
import model.VipUser;

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

	@FXML
	private Label errormessage;
	@FXML
	private Button postsbutton;
	@FXML
	private Button piebutton;

	private Connection connectDB;

	private User user;

	@FXML
	private TableView<Post> tableview;
	@FXML
	private TableColumn<Post, Integer> postIdcolumn;
	@FXML
	private TableColumn<Post, String> Authorcolumn;
	@FXML
	private TableColumn<Post, Integer> Likescolumn;
	@FXML
	private TableColumn<Post, Integer> Sharescolumn;
	@FXML
	private TableColumn<Post, LocalDateTime> Date_Timecolumn;
	@FXML
	private TableColumn<Post, String> Contentcolumn;

	@FXML
	private TextField mostlikesTextField;

	@FXML
	private TextField mostsharesTextField;

	@FXML
	private Label errorLabel;
	@FXML
	private Button vipbutton;
	@FXML
	private Button importbutton;

	public UserDashboardController() {
		this.connectDB = ApplicationModel.getInstance().getDatabaseConnection();
		this.user = ApplicationModel.getInstance().getUser();

	}

//	Function to deisplay the message when user loggs in to the user dashboard.
	public void displayMessage() {
		vipbutton.setVisible(!(user instanceof VipUser));
		piebutton.setVisible(user instanceof VipUser);
		importbutton.setVisible(user instanceof VipUser);
		String vipOrNonVipUser = "";
		if (user instanceof VipUser) {
			vipOrNonVipUser = "VIP User";
		} else {
			vipOrNonVipUser = "NonVip User";
		}

		LoginMessageLabel.setText("Hello " + user.getFirstName() + " " + user.getLastName()
				+ ", Welcome to the dashboard, you are a -" + vipOrNonVipUser);

	}

	
//Function to display all the details of user in the dashboard
	public void displayUserDetails() {
		UserNameLabel.setText(this.user.getUserName());
		PasswordLabel.setText(user.getPassword());
		FirstNameLabel.setText(user.getFirstName());
		LastNameLabel.setText(user.getLastName());
	}

	
//	fubction to swicth to edit scene whrn clicking on edit button
	public void EditButtonOnAction(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditScene.fxml"));
			Parent EditSceneParent = loader.load();
			EditSceneController esc = loader.getController();

			Scene EditScene = new Scene(EditSceneParent);

			
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

	
//	Function to siwtch the scene from userdashbaord to create scene when clicking on create button
	public void CreateButtonOnAction(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateScene.fxml"));
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

//	Function to populate posts on the table view in user dashboard.
	private void populatePosts(ObservableList<Post> observablelist) throws SQLException {
//Setting all the values in the table 
		postIdcolumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
		Authorcolumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
		Likescolumn.setCellValueFactory(new PropertyValueFactory<>("Likes"));
		Sharescolumn.setCellValueFactory(new PropertyValueFactory<>("Shares"));
		Contentcolumn.setCellValueFactory(new PropertyValueFactory<>("Content"));

		Date_Timecolumn.setCellValueFactory(new PropertyValueFactory<>("DateTime"));
		tableview.setItems(observablelist);

		tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				
				handleItemSelected(newSelection);
			}
		});
	}

	
//	functions to populate all posts
	public void populateAllPosts() throws SQLException {
		PostModel postModel = new PostModel();
		List<Post> posts = postModel.getAllPosts();
		ObservableList<Post> observablelist = FXCollections.observableArrayList(posts);

		populatePosts(observablelist);
	}


//	function to swicth the scene to display posts when clicking on any items in the table 
	private void handleItemSelected(Post newSelection) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DisplayPost.fxml"));
			Parent displayPostParent = loader.load();
			DisplayPostController dsp = loader.getController();
			dsp.setdetails(newSelection);
			Scene displayPostScene = new Scene(displayPostParent);

			
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

	
//	Function to search the posts using postid
	public void serachButtonOnAction(ActionEvent e) {
		PostModel postModel = new PostModel();
		List<Post> postsList = new ArrayList<>();
		if (searchtextField.getText().isEmpty()) {
			errormessage.setText("postid field cannot be empty");
			return;
		}
		try {
			int searchid = Integer.parseInt(searchtextField.getText());
			Post post = postModel.searchbyId(searchid);

			if (post == null) {
				errormessage.setText("Post with ID " + searchid + " does not exist.");
				return;
			}
			postsList.add(post);
			ObservableList<Post> observablelist = FXCollections.observableArrayList(postsList);
			populatePosts(observablelist);

		} catch (NumberFormatException e2) {
			errormessage.setText("postid field should be a positive integer");
		} catch (SQLException e1) {
			errormessage.setText("postid field  should be a positive integer");

		}

	}

	
//	Function to delete the post using post id
	public void deleteButtonOnAction(ActionEvent e) {

		if (searchtextField.getText().isEmpty()) {
			errormessage.setText("postid field cannot be empty");
			return;
		}
		try {
			int deleteid = Integer.parseInt(searchtextField.getText());
			PostModel postModel = new PostModel();

			int userid = user.getUserId();

			boolean isDeleted = postModel.deletepost(deleteid, userid);
			if (isDeleted) {

				populateAllPosts();
				errormessage.setText("Post successfully deleted. (Post ID:" + deleteid +")");
			} else {
				errormessage.setText("Post deletion failed. (Post ID:" + deleteid +") not found or does not belong to you.");
			}

		} catch (NumberFormatException e2) {
			errormessage.setText("postid field should be a positive integer");
		} catch (SQLException e1) {
			errormessage.setText("postid field  should be a positive integer");

		}

	}

	
//	Functions to get the top most likes using no of posts
	public void likesokButtonOnAction(ActionEvent e) {

		if (mostlikesTextField.getText().isEmpty()) {
			errormessage.setText("likes cannot be empty");
			return;
		}

		PostModel postModel = new PostModel();
		try {
			int N = Integer.parseInt(mostlikesTextField.getText());

			if (N <= 0) {
				errormessage.setText("Likes should be a positive integer");
				return;
			}
			List<Post> posts = postModel.toplikes(N);
			ObservableList<Post> observablelist = FXCollections.observableArrayList(posts);

			populatePosts(observablelist);

		} catch (NumberFormatException e2) {
			errormessage.setText("postid field should be a positive integer");
		} catch (SQLException e1) {
			errormessage.setText("postid field  should be a positive integer");

		}
	}

//	Functions to get the top most shares using no of posts
	public void sharesokButtonOnAction(ActionEvent e) {

		if (mostsharesTextField.getText().isEmpty()) {
			errormessage.setText("likes cannot be empty");
			return;
		}
		try {
			int N = Integer.parseInt(mostsharesTextField.getText());
			if (N <= 0) {
				errormessage.setText("Shares should be a positive integer");
				return;
			}
			PostModel postModel = new PostModel();

			List<Post> posts = postModel.topshares(N);
			ObservableList<Post> observablelist = FXCollections.observableArrayList(posts);

			populatePosts(observablelist);

		} catch (NumberFormatException e2) {
			errormessage.setText("shares  should be a positive integer");
		} catch (SQLException e1) {
			errormessage.setText("shares  should be a positive integer");

		}
	}

	
//	Function to export the posts using post id
	public void exportbuttonOnAction(ActionEvent e) {

		if (searchtextField.getText().isEmpty()) {
			errormessage.setText("post id  cannot be empty");
			return;
		}
		PostModel postModel = new PostModel();
		int postid = Integer.parseInt(searchtextField.getText());
		Post post = postModel.exportPostToCSV(postid);
		
		if (post == null) {
			errormessage.setText("Post with ID " + postid + " not found.");
			return;
		}

		try {
		
			FileChooser fileChooser = new FileChooser();
			
			File initialDirectory = new File(System.getProperty("user.home"));
			fileChooser.setInitialDirectory(initialDirectory);
			
			
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
			fileChooser.getExtensionFilters().add(extFilter);
			
		
			Stage stage = new Stage();
			File file = fileChooser.showSaveDialog(stage);
			
			if (file != null) {
			
				String filePath = file.getAbsolutePath();
				
				postModel.writeToCSV(filePath, post);
			}
			errormessage.setText("Post with ID " + postid + " exported to CSV successfully.");
		}catch (Exception ex) {
			errormessage.setText("Post with ID " + postid + " not found.");
		}	
	}
	
//	function to logout from the application

	public void logoutbuttonOnAction(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			Parent LoginSceneParent = loader.load();
			LoginController Controller = loader.getController();
			Scene loginScene = new Scene(LoginSceneParent);
			Stage stage = (Stage) CreateButton.getScene().getWindow();

			ApplicationModel.getInstance().setUser(null);

			stage.setScene(loginScene);
			stage.show();

		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Error loading Register.fxml: " + e1.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("An unexpected error occurred: " + e1.getMessage());
		}

	}

	
//	Function  to become vip user when clicking on upgrade 
	public void vipbuttonOnAction(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("VIP Confirmation Dialog");
		alert.setHeaderText("Would you like to subscribe to the application for a monthly fee of $0?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			UserModel userModel = new UserModel();
			Alert infoAlert = new Alert(AlertType.INFORMATION);
			infoAlert.setTitle("VIP Information Dialog");
			infoAlert.setHeaderText("Please log out and log in again to access VIP functionalities.");
			userModel.upgradeToVip(user);
			infoAlert.showAndWait();

		}
	}

//	Fubction to display pie chart of range of shares for vip users

	public void piebuttonOnAction(ActionEvent e) {

		PostModel postModel = new PostModel();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VipUserDashboard.fxml"));
			Parent VipUserDashboardParent = loader.load();
			VipController vipController = loader.getController();
			vipController.pie();
			Scene vipScene = new Scene(VipUserDashboardParent);
			Stage stage = (Stage) piebutton.getScene().getWindow();
			stage.setScene(vipScene);
			stage.show();

		} catch (Exception e3) {
			System.out.println(e3.getMessage());

		}
	}

//	function to show alert when there is error in importing
	private void alert(String message,String title,String header,AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();

	}

//	Function to import the bulk csv file
	private void importCSV() {
		PostModel postModel= new PostModel();
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
		File selectedFile = fileChooser.showOpenDialog(new Stage());
		System.out.println(selectedFile);

		if (selectedFile != null) {
			
try {
				List<String[]> records = postModel.readCSV(selectedFile);
				
				if (records != null) {

					for (String[] record : records) {
						System.out.println("please run");
						
						if (record.length >= 6) {
							System.out.println("Coming inside this");
							
							int id = Integer.parseInt(record[0]);
							Post postReturned =postModel.searchbyId(id);
							if (postReturned != null) {
								alert("duplicate post id","Import Error","Import Failed",AlertType.ERROR);
								return;
							}
							System.out.println(id);
							String content = record[1];
							System.out.println(content);
							String author = record[2];
							System.out.println(author);
							int likes = Integer.parseInt(record[3]);
							System.out.println(likes);
							int shares = Integer.parseInt(record[4]);
							System.out.println(shares);
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
							System.out.println(record[5]);
							LocalDateTime datetime = LocalDateTime.parse(record[5], formatter);

							// Assuming you have a Post constructor that accepts these parameters
							Post post = new Post(id, author, content, likes, shares, user.getUserId(), datetime);
							tableview.getItems().add(post);
							postModel.addPost(id, content, author, shares, likes, datetime);
						}
					}
					alert("Post imported successfully","Import Success","Import Completed",AlertType.CONFIRMATION);
				}

			}
		
			catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			
				alert("An error occurred while importing the CSV file. Please check the file format","Import Error","Import Failed",AlertType.ERROR);
			
			} catch (DateTimeParseException e) {
				System.out.println(e.getMessage());
				alert("An error occurred while importing the CSV file. Please check the file format of DateTime","Import Error","Import Failed",AlertType.ERROR);;				
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
				alert("An error occurred while importing the CSV file. Please check the file format ","Import Error","Import Failed",AlertType.ERROR);
				
			}

		}

	}

	
//	Function to call importcsv function when clicking on import button
	public void importbuttonOnAction(ActionEvent e) {
		importCSV();
	}
	
//	Funbctions to display all posts when clicking of show all posts button.
	public void postsbuttonOnAction(ActionEvent e) throws SQLException {
		
		populateAllPosts();
		
	}

}
