package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.ApplicationModel;
import model.DatabaseConnection;
import model.NonVipUser;
import model.PostModel;
import model.User;
import model.UserModel;
import model.VipUser;

public class UserModelTest {
	 
	

	@Before
	public void setUp() throws Exception {
		User user=new User(1,"TestUserName","TestPassword","TestFirstName","TestLastName");
    	
    	ApplicationModel.getInstance().setUser(user);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogin() {
		UserModel  userModel = new UserModel();
		  	String validUsername = "Manee";
	        String validPassword = "Manee";     
	        boolean result = userModel.login(validUsername, validPassword);
	        assertTrue(result);
	}
	
	@Test
	public void testLoginFailedCase() {
		UserModel  userModel = new UserModel();
		  	String validUsername = "Man";
	        String validPassword = "Man";

	        // Call the login function
	        boolean result = userModel.login(validUsername, validPassword);

	        // Assert that the login was successful
	        assertFalse(result);
	}
	
	

    @Test
    public void testIsUserRegisteredAlready_Positive() {
    	UserModel  userModel = new UserModel();

     
        String existingUsername = "Manee";

        // Call the isUserRegisteredAlready function
        boolean result = userModel.isUserRegisteredAlready(existingUsername);

        // Assert that the function correctly identifies the registered user
        assertTrue(result);
    }
    

    @Test
    public void testIsUserRegisteredAlready_Negative() {
    	UserModel  userModel = new UserModel();

     
        String nonexistingUsername = "TestRegister";

        // Call the isUserRegisteredAlready function
        boolean result = userModel.isUserRegisteredAlready(nonexistingUsername);

        // Assert that the function correctly identifies the registered user
        assertFalse(result);
    }
    
    @Test
    public void testUserDetailsUpdate_Positive() {
    	UserModel  userModel = new UserModel();
    	 Random random = new Random();

         // Generate a random user ID for testing
         int randomUserId = random.nextInt(50000) + 1;  
        // Assume an instance of User with valid updated details
        User editUser = new User(randomUserId,"testnewUsername", "testnewPassword", "testNewFirstName", "testNewLastName");

        // Call the userDetailsUpdate function
        boolean result = userModel.userdetailsupdate(editUser);

        // Assert that the function correctly updates user details
        assertTrue(result);
    }
    
    @Test
    public void testUserDetailsUpdate_InvalidUserId() {
        UserModel userModel = new UserModel();
        User editUser = new User(-1, "username", "password", "John", "Doe");
        boolean result = userModel.userdetailsupdate(editUser);
        assertFalse(result);
    }
    
    @Test
    public void testLoginVipUser() {
        
        UserModel userModel = new UserModel();
        boolean result = userModel.login("Samprus", "Sampreeth");
        assertTrue(result);
        User user = ApplicationModel.getInstance().getUser();
        assertTrue(user instanceof VipUser);
    }
    
    
    
    @Test
    public void testLogin_NonVipUser() {
     
        UserModel userModel = new UserModel();
        boolean result = userModel.login("Testing", "Testing");
        assertTrue(result);
        User user = ApplicationModel.getInstance().getUser();
        assertTrue(user instanceof NonVipUser);
    }
}



