package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Ers_Users;
import com.revature.utils.ConnectionUtil;

public class Ers_UsersDAO {
	public static Ers_Users current;
	
	public Ers_Users login(String ers_username, String ers_password) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "select * from ers_users where ers_username =? and ers_password = ?;";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1,  ers_username);
			ps.setString(2, ers_password);
			
			ResultSet rs = ps.executeQuery();
			
			//if anything gets returned at all, we know a user exists with that username/password pair. So we can return true
			//so we can create a new User object to send to the front end 
			if(rs.next()) {
				//return true; removed it bc of public User(this changed from boolean to User) login(String username, String password) {
                //*
				current = new Ers_Users(
					rs.getInt("ers_users_id"),
				    rs.getString("ers_username"),
				    rs.getString("ers_password"),
				    rs.getString("user_first_name"),
				    rs.getString("user_last_name"),
				    rs.getString("user_email"),
				    rs.getInt("user_role_id_fk")
				    );
				return current;
				//notice we're returning a password here... probably not best practice lol
				//in a REAL application, you'd probably want a User constructor with no password and send that around instead.
				}
		    }catch(SQLException e){
		    	
		    	System.out.println("LOGIN FAILED");
		    	e.printStackTrace();
		    }
				return null;//return false; *was changed to null bc of front end .
				//if no user is returned, return null. We will do a null check in the service layer.
		
	}

}
