import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.sql.Date;

public class PostModel {

	private Connection connectDB;
	private TextField searchtextField;
	private User user;
	public PostModel() {
		this.connectDB = ApplicationModel.getInstance().getDatabaseConnection();
		this.user=ApplicationModel.getInstance().getUser();
	}
		
	public String addPost(int postid, String postcontent, String postauthor,int postshares, int postlikes, LocalDateTime date_time) {
		try {
			
			Post newPost = new Post(postid,postcontent,postauthor,postshares,postlikes,user.getUserId(),date_time);
			
			String insertUserQuery = "INSERT INTO posts (id,content,author,likes,shares,userId,DateTime) VALUES (?, ?, ?, ?, ?,?,?)";
			PreparedStatement insertUserStatement = connectDB.prepareStatement(insertUserQuery);
			insertUserStatement.setInt(1, newPost.getId());
			insertUserStatement.setString(2, newPost.getContent());
			insertUserStatement.setString(3, newPost.getAuthor());
			insertUserStatement.setInt(4, newPost.getLikes());
			insertUserStatement.setInt(5, newPost.getShares());
			insertUserStatement.setInt(6, newPost.getuserId());
//			java.sql.Date sqlDate = java.sql.Date.valueOf(newPost.getDateTime().toLocalDate());
//			Time sqlTime = java.sql.Time.valueOf(newPost.getDateTime().toLocalTime());
			Timestamp timestamp = Timestamp.valueOf(newPost.getDateTime());
			insertUserStatement.setTimestamp(7, timestamp);
//			insertUserStatement.setDate(7, sqlDate);
//			insertUserStatement.setTime(7, sqlTime);
			insertUserStatement.executeUpdate();
			
			return "Post Created Sucessfully";
		}
		catch (SQLException e) {
			return e.getMessage();
		}
		
	}
	
	public List<Post> getAllPosts() throws SQLException {
		List<Post> posts = new ArrayList<>();
		String getAllPostsQuery= "SELECT * FROM posts";
		PreparedStatement statement = connectDB.prepareStatement(getAllPostsQuery);
		ResultSet resultSet = statement.executeQuery();
		
		
		while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String content = resultSet.getString("content");
            String author = resultSet.getString("author");
            int likes = resultSet.getInt("likes");
            int shares = resultSet.getInt("shares");
            int userid = resultSet.getInt("userId");
            Timestamp timestamp = resultSet.getTimestamp("DateTime");
            LocalDateTime dateTime = timestamp.toLocalDateTime();
//            java.sql.Date sqlDate = resultSet.getDate("DateTime");
//            
//            Time sqlTime = resultSet.getTime("DateTime");
////            String dateTimeString = resultSet.getString("datetime");
//            System.out.println(sqlDate);
//            System.out.println(sqlTime);
//            String dateAndTime = sqlDate.toString() + " " + sqlTime.toString();
//            LocalDateTime dateTime = LocalDateTime.parse(, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            

            Post post = new Post(id, content, author, likes, shares,userid,dateTime);
            posts.add(post);
        }
		
		return posts;
		
	}
	
	public Post searchbyId(int searchid) throws SQLException {
		
		String checkpostQuery = "SELECT * FROM posts WHERE id = '" + searchid+ "'";
		PreparedStatement checkpostStatement = connectDB.prepareStatement(checkpostQuery);
		
		ResultSet resultSet = checkpostStatement.executeQuery();
		Post post = null;
		if (resultSet.next()) {
			int id = resultSet.getInt("id");
			String content = resultSet.getString("content");
			String author = resultSet.getString("author");
			int likes = resultSet.getInt("likes");
			int shares = resultSet.getInt("shares");
			int userid = resultSet.getInt("userId");
			  String dateTimeString = resultSet.getString("datetime");
	            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			post = new Post(id, content, author, likes, shares, userid,dateTime);			
		}
		
		if (post == null) {
			return null;
		}
		
		return post;
	}

	public boolean deletepost(int postid,int userid) throws SQLException {
		String deleteQuery = "DELETE FROM posts WHERE id = '" + postid + "' AND userId = '" + user.getUserId() + "'";
		PreparedStatement deleteStatement = connectDB.prepareStatement(deleteQuery);
		int resultSet = deleteStatement.executeUpdate();
		return true;
			
		}
	
	public List<Post> toplikes(int N) throws SQLException {
		List<Post> posts = new ArrayList<>();
	    String toplikesquery = "SELECT * FROM posts ORDER BY likes DESC LIMIT ?";
        PreparedStatement preparedStatement = connectDB.prepareStatement(toplikesquery);
		preparedStatement.setInt(1, N);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String content = resultSet.getString("content");
            String author = resultSet.getString("author");
            int likes = resultSet.getInt("likes");
            int shares = resultSet.getInt("shares");
            int userid = resultSet.getInt("userId");
            Timestamp timestamp = resultSet.getTimestamp("DateTime");
            LocalDateTime dateTime = timestamp.toLocalDateTime();


            

            Post post = new Post(id, content, author, likes, shares,userid,dateTime);
            posts.add(post);
		

		}
		return posts;
	}
	
	public List<Post> topshares(int N) throws SQLException {
		List<Post> posts = new ArrayList<>();
	    String topsharesquery = "SELECT * FROM posts ORDER BY shares DESC LIMIT ?";
        PreparedStatement preparedStatement = connectDB.prepareStatement(topsharesquery);
		preparedStatement.setInt(1, N);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String content = resultSet.getString("content");
            String author = resultSet.getString("author");
            int likes = resultSet.getInt("likes");
            int shares = resultSet.getInt("shares");
            int userid = resultSet.getInt("userId");
            Timestamp timestamp = resultSet.getTimestamp("DateTime");
            LocalDateTime dateTime = timestamp.toLocalDateTime();


            

            Post post = new Post(id, content, author, likes, shares,userid,dateTime);
            posts.add(post);
		

		}
		return posts;
	}
		  
	    public void exportPostToCSV(int postId) {
	        try {
	        	  FileChooser fileChooser = new FileChooser();

	             // Set the initial directory (optional)
	             File initialDirectory = new File(System.getProperty("user.home"));
	             fileChooser.setInitialDirectory(initialDirectory);

	             // Set extension filters (optional)
	             FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
	             fileChooser.getExtensionFilters().add(extFilter);
	            // Show save dialog
	            Stage stage = new Stage();
	            File file = fileChooser.showSaveDialog(stage);

	            if (file != null) {
	                // The user has chosen a file
	                String filePath = file.getAbsolutePath();

	                String query = "SELECT * FROM posts WHERE id = ?";
	                PreparedStatement preparedStatement = connectDB.prepareStatement(query);
	                preparedStatement.setInt(1, postId);

	                ResultSet resultSet = preparedStatement.executeQuery();

	                // Check if the post with the specified ID exists
	                if (resultSet.next()) {
	                    // Retrieve post details
	                    int id = resultSet.getInt("id");
	                    String content = resultSet.getString("content");
	                    String author=resultSet.getString("author");
	                    int likes=resultSet.getInt("likes");
	                    int shares= resultSet.getInt("shares");
	                    int userId=resultSet.getInt("userId");
	                    

	                    // Save to CSV
	                    writeToCSV(filePath, id, content,author,likes,shares,userId);
	                    System.out.println("Post with ID " + postId + " exported to CSV successfully.");
	                } else {
	                    System.out.println("Post with ID " + postId + " not found.");
	                }
	            } else {
	                System.out.println("Export canceled by the user.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Other methods...

	    private void writeToCSV(String filePath, int id, String content,String author,int likes, int shares, int userId) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	            writer.write("ID,Content,Author,likes,shares,userId\n"); // Write CSV header
	            writer.write(id + "," + content + "," + author + "," + likes + "," + shares + "," + userId + "\n");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}


		

	




