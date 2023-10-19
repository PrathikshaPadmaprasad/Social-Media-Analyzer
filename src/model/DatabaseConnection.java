package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	 public Connection databaseLink;
	 
	 public Connection connect() {
		
		 try {
	           
	            String url = "jdbc:sqlite:database/databse.db";
	            // create a connection to the database
	            databaseLink = DriverManager.getConnection(url);
	            
	            System.out.println("Connection to SQLite has been established.");
	            return databaseLink;
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            return null;
	        } 
		 
	 }

}
