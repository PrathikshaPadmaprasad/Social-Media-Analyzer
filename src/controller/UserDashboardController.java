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

	public void displayUserDetails() {
		UserNameLabel.setText(this.user.getUserName());
		PasswordLabel.setText(user.getPassword());
		FirstNameLabel.setText(user.getFirstName());
		LastNameLabel.setText(user.getLastName());
	}

	public void EditButtonOnAction(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditScene.fxml"));
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

	public void populatePosts(ObservableList<Post> observablelist) throws SQLException {

		postIdcolumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
		Authorcolumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
		Likescolumn.setCellValueFactory(new PropertyValueFactory<>("Likes"));
		Sharescolumn.setCellValueFactory(new PropertyValueFactory<>("Shares"));
		Contentcolumn.setCellValueFactory(new PropertyValueFactory<>("Content"));

		Date_Timecolumn.setCellValueFactory(new PropertyValueFactory<>("DateTime"));
		tableview.setItems(observablelist);

		tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				// Handle the selected item
				handleItemSelected(newSelection);
			}
		});
	}

	public void populateAllPosts() throws SQLException {
		PostModel postModel = new PostModel();
		List<Post> posts = postModel.getAllPosts();
		ObservableList<Post> observablelist = FXCollections.observableArrayList(posts);

		populatePosts(observablelist);
	}

	private void handleItemSelected(Post newSelection) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/DisplayPost.fxml"));
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
//			System.out.println(post);
			postsList.add(post);
			ObservableList<Post> observablelist = FXCollections.observableArrayList(postsList);
			populatePosts(observablelist);

		} catch (NumberFormatException e2) {
			errormessage.setText("postid field should be a positive integer");
		} catch (SQLException e1) {
			errormessage.setText("postid field  should be a positive integer");

		}

	}

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
			} else {
				errormessage.setText("Post deletion failed. Post ID: " + deleteid);
			}

		} catch (NumberFormatException e2) {
			errormessage.setText("postid field should be a positive integer");
		} catch (SQLException e1) {
			errormessage.setText("postid field  should be a positive integer");

		}

	}

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

	public void exportbuttonOnAction(ActionEvent e) {

		if (searchtextField.getText().isEmpty()) {
			errormessage.setText("post id  cannot be empty");
			return;
		}

		PostModel postModel = new PostModel();
		int postid = Integer.parseInt(searchtextField.getText());

//		postModel.exportPostToCSV(postid );
		if (postModel.exportPostToCSV(postid)) {

			errormessage.setText("Post with ID " + postid + " exported to CSV successfully.");
		} else {

			errormessage.setText("Post with ID " + postid + " not found.");
		}
	}

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

	@FXML
	private Button piebutton;

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

	public void alert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Import Error");
		alert.setHeaderText("Error importing CSV");
		alert.setContentText("An error occurred while importing the CSV file. Please check the file format.");
		alert.showAndWait();

	}

	public void importCSV() {
		PostModel postModel= new PostModel();
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
		File selectedFile = fileChooser.showOpenDialog(new Stage());

		if (selectedFile != null) {
			System.out.println("uygfurgu");
try {
		
				List<String[]> records = postModel.readCSV();
				
				if (records != null) {
					System.out.println("Hi");

					for (String[] record : records) {
						System.out.println("please run");
						
						if (record.length >= 6) {
							System.out.println(record);
							int id = Integer.parseInt(record[0]);
							String author = record[1];
							int likes = Integer.parseInt(record[2]);
							int shares = Integer.parseInt(record[3]);
							String content = record[4];
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");
							LocalDateTime datetime = LocalDateTime.parse(record[5], formatter);

							// Assuming you have a Post constructor that accepts these parameters
							Post post = new Post(id, author, content, likes, shares, user.getUserId(), datetime);
							tableview.getItems().add(post);
							postModel.addPost(id, content, author, shares, likes, datetime);
						}
					}
				}

			}
		
			catch (NumberFormatException e) {
				alert();
			
			} catch (DateTimeParseException e) {
				alert();				
			
			} catch (Exception e) {
			
				alert();
				
			}

		}

	}

	public void importbuttonOnAction(ActionEvent e) {
		importCSV();
	}
	
	public void postsbuttonOnAction(ActionEvent e) throws SQLException {
		
		populateAllPosts();
		
	}

}
