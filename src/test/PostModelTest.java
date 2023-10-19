package test;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import model.ApplicationModel;
import model.Post;
import model.PostModel;
import model.User;
import model.UserModel;

public class PostModelTest {
//	private Connection connectDB;

    private PostModel post;

    @Before
    public void setUp() {
    	User user=new User(1,"TestUserName","TestPassword","TestFirstName","TestLastName");
    	
    	ApplicationModel.getInstance().setUser(user);
    	post = new PostModel();

        
    }

    @After
    public void tearDown() {
        
    }

//   TextCase:1
    @Test
    public void testAddPost() {
    	
    	Random random = new Random();
    	int rand = 60000;
    	while (true){
    	    rand = random.nextInt(70000);
    	    if(rand !=0) break;
    	}
        int postId = rand;
        String postContent = "Test post content";
        String postAuthor = "Test author";
        int postShares = 10;
        int postLikes = 5;
        String inputDateTimeString = "12/09/2019 10:00";
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {
          
            LocalDateTime dateTime = LocalDateTime.parse(inputDateTimeString, formatter);

            // Call the addPost method
            String result = post.addPost(postId, postContent, postAuthor, postShares, postLikes, dateTime);

            
            assertEquals("Post Created Sucessfully", result);
        } catch (Exception e) {
            fail("An unexpected exception occurred: " + e.getMessage());
        }
    }
    
    
    @Test
    public void testAddPostFailedCase() {
    	
    	
        int postId = 204;
        String postContent = "Test post content";
        String postAuthor = "Test author";
        int postShares = 10;
        int postLikes = 5;
        String inputDateTimeString = "2020-09-09 10:00:00";

        // Define a custom date-time format matching the input format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            // Parse the string to LocalDateTime using the defined formatter
            LocalDateTime dateTime = LocalDateTime.parse(inputDateTimeString, formatter);

            // Call the addPost method
            post.addPost(postId, postContent, postAuthor, postShares, postLikes, dateTime);
            String result = post.addPost(postId, postContent, postAuthor, postShares, postLikes, dateTime);
            // Check if the result is as expected (e.g., "Post Created Successfully")
            assertEquals("Post creation failed:It could be because id you are creating alrtready exists", result);
        } catch (Exception e) {
            fail("An unexpected exception occurred: " + e.getMessage());
        }
    }
    
    
    
    @Test
    public void testSearch( ) throws SQLException {
    	PostModel postModel= new PostModel();
    	Post returnpost=postModel.searchbyId(204);
    
    	assertEquals(returnpost.getId(), 204);
    	
    	
    	
    }
    
    @Test
    public void testSearchFailedCase( ) throws SQLException {
    	PostModel postModel= new PostModel();
    	Post returnPost = postModel.searchbyId(99921232);
    	 assertNull(returnPost);    	
    	
    	
    }
    
//    @Test
//    public void testDeletePost() {
//    	PostModel postModel= new PostModel();
//    	try {
//			assertTrue(postModel.deletepost(203, 2));
//			
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
    	
//    }
    
    @Test
    public void testDeletePostFailed() {
    	PostModel postModel= new PostModel();
    	try {
			assertFalse(postModel.deletepost(13, 13));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    
   
    
}
    
    
    @Test
    public void testTopNLikes() throws SQLException {
    	
    	  int N = 5;  // Change this to the number of top likes you want to retrieve
    	  PostModel postModel= new PostModel();
          List<Post> topLikesPosts = postModel.toplikes(N);

          // Assuming the test data in your database is sorted by likes in descending order
          // Check if the number of retrieved posts matches the expected number
          assertEquals(5, topLikesPosts.size());
    }
    
    
    @Test
    public void testTopNLikeswithNegativeValues() throws SQLException {
    	
    	  int N = -5;  // Change this to the number of top likes you want to retrieve
    	  PostModel postModel= new PostModel();
          List<Post> topLikesPosts = postModel.toplikes(N);
          System.out.println(topLikesPosts);

          // Assuming the test data in your database is sorted by likes in descending order
          // Check if the number of retrieved posts matches the expected number
          assertTrue(topLikesPosts.isEmpty());
    }
    
 
  
  @Test
  public void testTopNShares() throws SQLException {
  	
  	  int N = 3;  // Change this to the number of top likes you want to retrieve
  	  PostModel postModel= new PostModel();
        List<Post> topSharesPosts = postModel.topshares(N);

        // Assuming the test data in your database is sorted by likes in descending order
        // Check if the number of retrieved posts matches the expected number
        assertEquals(3, topSharesPosts.size());
  }
  
  
  
  @Test
  public void testTopNSharesswithNegativeValues() throws SQLException {
  	
  	  int N = -5;  
  	  PostModel postModel= new PostModel();
        List<Post> topSharesPosts = postModel.topshares(N);
        System.out.println(topSharesPosts);

       
        assertTrue(topSharesPosts.isEmpty());
  }
  
  
  @Test
  public void testExportToCsv() {
	  PostModel postModel= new PostModel(); 
	  Post returnpost=postModel.exportPostToCSV(103);

	  assertEquals(returnpost.getId(),103);
	  
  }
  
  @Test
  public void testExportToCsvFailedCase() {
	  PostModel postModel= new PostModel(); 
	  Post returnpost=postModel.exportPostToCSV(107);
//	  TODO
	  assertNull(returnpost);
	  
  }
  
  
  @Test
  public void importCsv() {
  PostModel postModel = new PostModel();
  String temp ="/Users/prathikshacp/Desktop/13posttest.csv";
	File file = new File(temp);
	List<String[]> records = postModel.readCSV(file);


	assertEquals(1, records.size());

    	
    }
  
}


  

