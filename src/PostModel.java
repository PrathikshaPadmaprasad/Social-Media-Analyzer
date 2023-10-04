import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.control.TextField;

public class PostModel {

	private Connection connectDB;
	private TextField searchtextField;
	
	public PostModel() {
		this.connectDB = ApplicationModel.getInstance().getDatabaseConnection();
	}
		
	public void addPost(int postid, String postcontent, String postauthor,int postshares, int postlikes, int loggedInUserId) throws SQLException {
		Post newPost = new Post(postid,postcontent,postauthor,postshares,postlikes,loggedInUserId);
		
		String insertUserQuery = "INSERT INTO posts (id,content,author,likes,shares,userId) VALUES (?, ?, ?, ?, ?,?)";
		PreparedStatement insertUserStatement = connectDB.prepareStatement(insertUserQuery);
		insertUserStatement.setInt(1, newPost.getId());
		insertUserStatement.setString(2, newPost.getContent());
		insertUserStatement.setString(3, newPost.getAuthor());
		insertUserStatement.setInt(4, newPost.getLikes());
		insertUserStatement.setInt(5, newPost.getShares());
		insertUserStatement.setInt(6, newPost.getuserId());
		insertUserStatement.executeUpdate();
		System.out.println("Post created sucessfully");
		
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
            

            Post post = new Post(id, content, author, shares, likes, userid);
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
			System.out.println("Hello world");
			int id = resultSet.getInt("id");
			String content = resultSet.getString("content");
			String author = resultSet.getString("author");
			int likes = resultSet.getInt("likes");
			int shares = resultSet.getInt("shares");
			int userid = resultSet.getInt("userId");
			post = new Post(id, content, author, shares, likes, userid);			
		}
		
		if (post == null) {
			return null;
		}
		
		return post;
	}

		
}
	




