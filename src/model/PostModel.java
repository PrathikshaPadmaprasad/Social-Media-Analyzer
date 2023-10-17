package model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.List;



public class PostModel {

	

	private Connection connectDB;
	
	private User user;

	public PostModel() {
		this.connectDB = ApplicationModel.getInstance().getDatabaseConnection();
		this.user = ApplicationModel.getInstance().getUser();
	}

	public String addPost(int postid, String postcontent, String postauthor, int postshares, int postlikes,
			LocalDateTime date_time) {
		try {

			Post newPost = new Post(postid, postcontent, postauthor, postshares, postlikes, user.getUserId(),
					date_time);

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
		} catch (SQLException e) {
			return "Post creation failed:It could be because id you are creating alrtready exists";
		}

	}

	public List<Post> getAllPosts() throws SQLException {
		List<Post> posts = new ArrayList<>();
		String getAllPostsQuery = "SELECT * FROM posts";
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
			Post post = new Post(id, content, author, likes, shares, userid, dateTime);
			posts.add(post);

		}

		return posts;

	}

	public Post searchbyId(int searchid) throws SQLException {

		String checkpostQuery = "SELECT * FROM posts WHERE id = '" + searchid + "'";
		PreparedStatement checkpostStatement = connectDB.prepareStatement(checkpostQuery);
		ResultSet resultSet = checkpostStatement.executeQuery();
		
		Post post = null;
		if (resultSet.next()) {
			int id = resultSet.getInt("id");
			if (id != searchid) {
				return null;
			}
			String content = resultSet.getString("content");
			String author = resultSet.getString("author");
			int likes = resultSet.getInt("likes");
			int shares = resultSet.getInt("shares");
			int userid = resultSet.getInt("userId");
			Timestamp timestamp = resultSet.getTimestamp("DateTime");
			LocalDateTime dateTime = timestamp.toLocalDateTime();
			post = new Post(id, content, author, likes, shares, userid, dateTime);
		}

		if (post == null) {
			return null;
		}

		return post;
	}

	public boolean deletepost(int postid, int userid) throws SQLException {

		String checkQuery = "SELECT COUNT(*) FROM posts WHERE id = ? AND userId = ?";
		PreparedStatement checkStatement = connectDB.prepareStatement(checkQuery);
		checkStatement.setInt(1, postid);
		checkStatement.setInt(2, userid);
		ResultSet rs = checkStatement.executeQuery();

		if (rs.next() && rs.getInt(1) == 0) {
			return false; 
		}

		else {
			String deleteQuery = "DELETE FROM posts WHERE id = '" + postid + "' AND userId = '" + user.getUserId()
					+ "'";
			PreparedStatement deleteStatement = connectDB.prepareStatement(deleteQuery);
			int resultSet = deleteStatement.executeUpdate();
			return true;

		}

	}

	public List<Post> toplikes(int N) throws SQLException {
		List<Post> posts = new ArrayList<>();
		if (N < 0) {
			return posts;
		}
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

			Post post = new Post(id, content, author, likes, shares, userid, dateTime);
			posts.add(post);

		}
		return posts;
	}

	public List<Post> topshares(int N) throws SQLException {
		List<Post> posts = new ArrayList<>();
		if (N < 0) {
			return posts;
		}
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
			Post post = new Post(id, content, author, likes, shares, userid, dateTime);
			posts.add(post);

		}
		return posts;
	}

	public Post exportPostToCSV(int postId) {
		try {

			String query = "SELECT * FROM posts WHERE id = ?";
			PreparedStatement preparedStatement = connectDB.prepareStatement(query);
			preparedStatement.setInt(1, postId);

			ResultSet resultSet = preparedStatement.executeQuery();

			Post post = null;
			// Check if the post with the specified ID exists
			if (resultSet.next()) {
				// Retrieve post details
				int id = resultSet.getInt("id");
				if (id != postId) {
					return null;
				}
				String content = resultSet.getString("content");
				String author = resultSet.getString("author");
				int likes = resultSet.getInt("likes");
				int shares = resultSet.getInt("shares");
				int userId = resultSet.getInt("userId");
				Timestamp timestamp = resultSet.getTimestamp("DateTime");
				LocalDateTime dateTime = timestamp.toLocalDateTime();
				post = new Post(id,  content,  author, likes,  shares, userId,dateTime);
				
			}
			return post;
		} catch (SQLException e) {	
			
			return null;
		}
	}

	public void writeToCSV(String filePath, Post post) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write("ID,Content,Author,likes,shares,userId,dateTime\n");
			writer.write(post.getId() + "," + post.getContent() + "," + post.getAuthor() + "," + post.getLikes() + "," + post.getShares() + "," + post.getuserId() + "," + post.getDateTime()
					+ "\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ResultSet piechartforvip() {
		try {
			String query = "SELECT " + "COUNT(CASE WHEN shares >= 0 AND shares <= 99 THEN 1 END) AS range_0_to_99, "
					+ "COUNT(CASE WHEN shares >= 100 AND shares <= 999 THEN 1 END) AS range_100_to_999, "
					+ "COUNT(CASE WHEN shares >= 1000 THEN 1 END) AS range_1000_and_above "
					+ "FROM posts";
			PreparedStatement preparedStatement = connectDB.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	public List<String[]> readCSV(File filePath) {
		
		
		
        List<String[]> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    // Skip the first line (header)
                    isFirstLine = false;
                    continue;
                }
          
                String[] values = line.split(",");
                records.add(values);
                
            }
        }catch (FileNotFoundException e) {
         
          System.out.println("FILE NOT FOUND") ;
        }
            catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        return records;
	}
	
	
	
}
