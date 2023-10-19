package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserModel {

	private Connection connectDB;

	public UserModel() {
		this.connectDB = ApplicationModel.getInstance().getDatabaseConnection();
	}

	public boolean login(String username, String password) {
		try {
			User user;
			String query = "SELECT * FROM users WHERE Username ='" + username + "' and Password='" + password + "'";

			Statement statement = connectDB.createStatement();

			// Execute the query
			ResultSet resultSet = statement.executeQuery(query);

			if (!resultSet.next()) {
				return false;
			}

			int userid = resultSet.getInt("userId");
			String firstname = resultSet.getString("FirstName");
			String lastname = resultSet.getString("LastName");
			String vipStatus = resultSet.getString("VipStatus");
			System.out.println(vipStatus);

			if (vipStatus.equals("No")) {
				user = new NonVipUser(userid, username, password, firstname, lastname);
			} else {
				user = new VipUser(userid, username, password, firstname, lastname);
			}
			ApplicationModel.getInstance().setUser(user);

			return true;

		} catch (Exception e1) {
			return false;
		}

	}

	public boolean register(User user) {
		try {
			String insertUserQuery = "INSERT INTO Users (userId,UserName, Password, FirstName, LastName,VipStatus) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement insertUserStatement = connectDB.prepareStatement(insertUserQuery);
			insertUserStatement.setInt(1, user.getUserId());
			insertUserStatement.setString(2, user.getUserName());
			insertUserStatement.setString(3, user.getPassword());
			insertUserStatement.setString(4, user.getFirstName());
			insertUserStatement.setString(5, user.getLastName());
			insertUserStatement.setString(6, "No");
			insertUserStatement.executeUpdate();

			return true;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean isUserRegisteredAlready(String username) {
		try {

			// Check if the username already exists in the database
			String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
			PreparedStatement checkUserStatement = connectDB.prepareStatement(checkUserQuery);
			checkUserStatement.setString(1, username);
			ResultSet resultSet = checkUserStatement.executeQuery();
			int userCount = resultSet.getInt(1);
			System.out.println(userCount);
			if (userCount > 0) {
				return true;
			}
			return false;

		} catch (SQLException e) {
			return false;
		}
	}

	public int getNewRegistrationUserId() {
		try {
			String countquery = "SELECT COUNT(*) AS row_count FROM users";
			PreparedStatement countStatement;
			countStatement = connectDB.prepareStatement(countquery);
			ResultSet r = countStatement.executeQuery();
			int row = 0;
			if (r.next()) {
				row = r.getInt("row_count");
			}
			return ++row;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return -1;
		}
	}

	public boolean userdetailsupdate(User edituser) {
		try {

			// Check if the username already exists in the database
			String UpdateQuery = "Update Users Set UserName='" + edituser.getUserName() + "',Password='"
					+ edituser.getPassword() + "',FirstName='" + edituser.getFirstName() + "',LastName='"
					+ edituser.getLastName() + "' Where userId=" + ApplicationModel.getInstance().getUser().getUserId();
			PreparedStatement checkUserStatement = connectDB.prepareStatement(UpdateQuery);

			int rowsUpdated = checkUserStatement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("updated");

				ApplicationModel.getInstance().setUser(edituser);
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			return false;
		}
	}

	public void upgradeToVip(User user) {

		try {
			String UpdateQuery = "Update Users Set VipStatus='Yes' where userId='" + user.getUserId() + "'";
			PreparedStatement checkUserStatement = connectDB.prepareStatement(UpdateQuery);

			checkUserStatement.executeUpdate();

		} catch (Exception e) {
			System.out.println("Update Unscucessfull");
		}

	}
}