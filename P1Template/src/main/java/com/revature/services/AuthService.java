package com.revature.services;

import com.revature.daos.Ers_UsersDAO;
import com.revature.models.Ers_Users;

//The service layer is where we put any other business logic that doesn't deal with:
//parsing HTTP Requests and sending Responses (which is the job of the Controllers)
//talking to the database (which is the job of the DAOs)
//So in this case, we want to take in a LoginDTO, and determine if its values get a "true" returned from the DAO
//In a more fleshed out application, this class would create a new User object from the DAO and send it to the controller

public class AuthService {
	
	//AuthDAO aDAO = new AuthDAO();
	Ers_UsersDAO aDAO = new Ers_UsersDAO();
	
	public Ers_Users login(String username, String password) {
		
		if(aDAO.login(username, password) != null) {
			return aDAO.login(username, password);
			
			//if the username and password return a User from the DAO, send the User back.
			
			
		}
		return null; //if login fails, return null
	}

}
