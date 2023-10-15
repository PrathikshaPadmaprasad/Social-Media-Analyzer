package model;
import java.sql.Connection;

public class ApplicationModel {
	
	private static ApplicationModel instance;
	
	private DatabaseConnection databaseConnection;
	
	private Connection connectDB;
	 
	private User user;
	
//	Private constructor created to use singleton pattern
	private ApplicationModel() {
        this.databaseConnection = new DatabaseConnection();
        this.connectDB = this.databaseConnection.connect();
    }

//	To get the instance of main model class and use it in other classes 
    public static ApplicationModel getInstance() {
        if (instance == null) {
            instance = new ApplicationModel();
        }
        return instance;
    }

    public Connection getDatabaseConnection() {
        return this.connectDB;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
