import java.sql.Connection;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

private Connection connectDB;

private User user;


public CreateController() {
	this.connectDB=ApplicationModel.getInstance().getDatabaseConnection();
	this.user=ApplicationModel.getInstance().getUser();
}
	
public void createButtonOnAction(ActionEvent e) throws SQLException {
	int postid = Integer.parseInt(postidTextField.getText().trim());
	String postcontent = contentTextField.getText().trim();
	String postauthor = authorTextField.getText().trim();
	int postshares= Integer.parseInt(sharesTextField.getText().trim());
	int postlikes= Integer.parseInt(likesTextField.getText().trim());	
	
	PostModel postModel=new PostModel();
	
	postModel.addPost(postid,postcontent,postauthor,postshares,postlikes,user.getUserId());
	
	}
	




}
