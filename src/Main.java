
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class Main extends Application {
	
	private DatabaseConnection connectnow;

	private Connection connectDB;
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) throws IOException {
		
//		GET THE INSTANCE OF MAIN MODEL IN MAIN.JAVA
		connectDB=ApplicationModel.getInstance().getDatabaseConnection();
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));

		Parent root = fxmlLoader.load();
		LoginController loginController= fxmlLoader.getController();
	
		primaryStage.setTitle("Data Analytics Hub"); // Set the stage title
		
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    System.out.println("Closing the application...");
                    
                    primaryStage.close();
                }
            });
        });
		primaryStage.setScene(new Scene(root, 835, 505));/* create a Scene object pass it here */ // Place the scene in
																									// the stage
		primaryStage.show(); // Display the stage

	}

	/**
	 * The main method is only needed for the IDE with limited JavaFX support. Not
	 * needed for running from the command line.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}