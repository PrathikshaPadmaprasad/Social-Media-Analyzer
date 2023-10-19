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
		User user = new User(4, "Adi ", "Adi", "Adi", "Adi");

		ApplicationModel.getInstance().setUser(user);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogin() {
		UserModel userModel = new UserModel();
		String validUsername = "Prat!";
		String validPassword = "Prat@1997";
		boolean result = userModel.login(validUsername, validPassword);
		assertTrue(result);
	}

	@Test
	public void testLoginFailedCase() {
		UserModel userModel = new UserModel();
		String validUsername = "Man";
		String validPassword = "Man";

		// Call the login function
		boolean result = userModel.login(validUsername, validPassword);

		// Assert that the login was successful
		assertFalse(result);
	}

	@Test
	public void testIsUserRegisteredAlready_Positive() {
		UserModel userModel = new UserModel();

		String existingUsername = "Prat!";

		// Call the isUserRegisteredAlready function
		boolean result = userModel.isUserRegisteredAlready(existingUsername);

		// Assert that the function correctly identifies the registered user
		assertTrue(result);
	}

	@Test
	public void testIsUserRegisteredAlready_Negative() {
		UserModel userModel = new UserModel();

		String nonexistingUsername = "user!!";

		boolean result = userModel.isUserRegisteredAlready(nonexistingUsername);

		assertFalse(result);
	}

	@Test
	public void testUserDetailsUpdate_Positive() {
		UserModel userModel = new UserModel();

		boolean result = userModel.login("Sampreeth", "Sam@1234");
		if (result) {
			// Assume an instance of User with valid updated details
			User editUser = new User(2, "Sampreeth!", "Sam1234", "Sam", "Preeth");

			// Call the userDetailsUpdate function
			boolean result1 = userModel.userdetailsupdate(editUser);

			// Assert that the function correctly updates user details
			assertTrue(result1);
		}
	}

	@Test
	public void testUserDetailsUpdate_InvalidUserId() {

		UserModel userModel = new UserModel();
		boolean loginResult = userModel.login("Prat!", "Prat@1997");
		assertTrue(loginResult);

		User invalidUser = new User(1001, "testInvalidUsername", "testInvalidPassword", "testInvalidFirstName",
				"testInvalidLastName");

		boolean result = userModel.userdetailsupdate(invalidUser);

		assertFalse(result);
	}

	@Test
	public void testLoginVipUser() {

		UserModel userModel = new UserModel();
		boolean result = userModel.login("Prat!", "Prat@1997");
		assertTrue(result);
		User user = ApplicationModel.getInstance().getUser();
		assertTrue(user instanceof VipUser);
	}

	@Test
	public void testLogin_NonVipUser() {

		UserModel userModel = new UserModel();
		boolean result = userModel.login("testnewUsername", "testnewPassword");
		assertTrue(result);
		User user = ApplicationModel.getInstance().getUser();
		assertTrue(user instanceof NonVipUser);
	}
}
