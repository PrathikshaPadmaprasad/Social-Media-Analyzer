
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ApplicationModel;
import model.DatabaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;



public class Main extends Application {
	
	private DatabaseConnection connectnow;

	private Connection connectDB;
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) throws IOException {
		
		//Get the instance of main model in main.java
		connectDB=ApplicationModel.getInstance().getDatabaseConnection();
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));

		Parent root = fxmlLoader.load();
		LoginController loginController= fxmlLoader.getController();
		
		//	set the stage title
		primaryStage.setTitle("Data Analytics Hub"); 
		
		// Set up a close request event handler
        primaryStage.setOnCloseRequest(event -> {
            event.consume();  

            // Show a confirmation dialog
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                   
                	try {
						connectDB.close();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
                    System.out.println("Closing the application...");
                    
                    primaryStage.close();
                }
            });
        });
		primaryStage.setScene(new Scene(root, 864, 720));
		primaryStage.show(); 

	}

	
	public static void main(String[] args) {
		launch(args);
	}
}