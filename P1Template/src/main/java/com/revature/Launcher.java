package com.revature;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.daos.Ers_ReimbursementDAO;
import com.revature.models.Ers_Reimbursement;
import com.revature.utils.ConnectionUtil;

import Controllers.*;
import io.javalin.Javalin;

public class Launcher {
	
	

	public static void main(String[] args) {
		
		//to test the methods
		Ers_ReimbursementDAO rDAO = new Ers_ReimbursementDAO();
		
		System.out.println("Welcome to Employee Reimbursement System! Feel free to browse through our website, you'll enjoy it !!!");
		
		//testing if the connection works
		try(Connection conn = ConnectionUtil.getConnection()){
			System.out.println("CONNECTION SUCCESSFUL !");
			
		}catch(SQLException e) {
			//if connection fails,...
			System.out.println("Connection failed...");
			e.printStackTrace();
			
		}
		
		//To test methods
		System.out.println(rDAO.getReimbursementsByUserId(1));
		
		// instantiate an object then run the method for inserting Reimbursements =>rDAO.insertReimbursements()
		
		System.out.println(rDAO.getReimbursements()); //Testing Get all by Fin Managers
		
		System.out.println(rDAO.getReimbursementsByStatus(3)); //Testing Get all by status
		
		System.out.println(rDAO.updateStatusOfReimbursements(2, 1)); // Testing Update Reimbursements
		System.out.println(rDAO.getReimbursements());//to see if update took place
		
    //CREATE a javalin object 
		Javalin app = Javalin.create(
				
				//the config lambda 
				config -> {
					config.enableCorsForAllOrigins(); //this lets us process HTTP requests from any origin
				}
				
				).start(4000); // port 4000 where requests are sent to.
		
		//endpoint handlers below--------------------------
		
		//Instantiating Reimbursement Controller
		Ers_ReimbursementController rc = new Ers_ReimbursementController();
		
		
				//app.get() is the javalin method that takes in GET requests. It will return all employees from the DB.
				//this handler takes in GET requests ending in /employees, and sends them to the getEmployeesHandler
				//the request in postman would look something like: localhost:3000/employees
				app.get("/reimbursements", rc.getReimbursementHandler);
				//what does /employees relate to? it's something we define. we want requests ending in /employees to get all employees
				
				
				//app.post() is the javalin method that takes in POST requests. It will insert employee data into the DB.
				//how come we can have two endpoints of "/employees"? that's because one is for a GET, while the other is a POST
				app.post("/reimbursements", rc.insertReimbursementsHandler);
				
				
				//app.put() is a javalin method that takes PUT requests. 
				//It will take in two pieces of data: the role title (in the path parameter) and the salary (in the Request body)
				//:title? This is a PATH PARAMETER. Whatever the user inserts here in the request will be used in the controller
				
				app.put("/reimbursements/:status_id",rc.updateReimbursementStatusHandler);			
				
				
								
				app.get("/reimbursementsByUser/:user_id", rc.getReimbursementsByUserIdHandler);
				
				
				app.get("/reimbursementsByStatus/:reimb_status", rc.getReimbursementsByStatusHandler);
				
				
				AuthController ac = new AuthController();
				
				//endpoint handler for login
				app.post("/login", ac.loginHandler);
				
		
	}	
}
//Welcome to P1! 		
//If you're reading this, you've successfully cloned your repo and imported the template		
//Do your coding in this project, and don't forget to save/push your progress with:
//git add.   		//git commit -m"message"    		//git push		
//yes, you WILL need to push to your repo. The clients will want to see your project repos in your portfolios.
//here's a dog to help you on your way. Have fun!
	
 //              __
 //          (___()'`;
//          /,    /`
//          \\"--\\