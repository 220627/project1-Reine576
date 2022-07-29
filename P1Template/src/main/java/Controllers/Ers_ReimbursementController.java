package Controllers;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.revature.daos.Ers_ReimbursementDAO;
import com.revature.models.Ers_Reimbursement;

import io.javalin.http.Handler;

//The Controller layer is where HTTP requests get sent after Javalin directs them.
//It's in this layer that we'll translate any JSON into Java and vice versa
//We'll either be getting data from the service or DAO layer to send back to the front end 
//OR we'll be receiving data from the front end to send to the database
public class Ers_ReimbursementController {

	public static Logger log = LogManager.getLogger(); //for the log. Import the apache log...
	
	//We need an Ers_ReimbursementDAO object to use its methods
	
	Ers_ReimbursementDAO rDAO = new Ers_ReimbursementDAO();
	
	
	//This Handler will get the HTTP GET Request for all reimbursements (used by Finance Man), ?????????????????????????///
	//then it will collect the data and send it back in an HTTP Response
	public Handler getReimbursementHandler = (ctx) ->{
		// ctx is the Context object! 
		//This object contains a bunch of method that we can use to take in HTTP Requests and send HTTP Responses
		
		if(AuthController.ses != null) { //if the user is logged in, they can access this functionality
			
			//We need an ArrayList of Reimbursements, courtesy of our Ers_ReimbursementDAO
			ArrayList<Ers_Reimbursement> reimbursements = rDAO.getReimbursements();
			
			//create a GSON object, which has methods to convert our Java object into JSON
			Gson gson = new Gson();
			
			//use the GSON .toJson() method to turn our Java into JSON String (JSON is always in String format on the Java side)
			String JSONreimbursements = gson.toJson(reimbursements);//reimbursements is the ArrayList of our reimbursement data
			
			log.info("User was able to view all reimbursements!");  //line in relation to logging
			
			
			//use ctx to provide an HTTP response containing our JSON string of reimbursements (which is what was requested)
			ctx.result(JSONreimbursements);//ctx.result() sends a response back (this is where our data goes)
			
			ctx.status(200);//ctx.status() sets the HTTP status code. 200 stands for "OK", the generic success code
			           System.out.println(" I'm doing well ?????");
				
		}else {//if the user is NOT logged in (aka AuthController.ses wil be null)
			
			log.info("Finance Manager could not view all reimbursements!");  //line in relation to logging
			ctx.result("YOU ARE NOT LOGGED IN!");
			ctx.status(401); //"forbidden" access code
			
			
		}
		
		
		};//semicolon after curly brace? That's lambdas for you.
	
	
			//This Handler will get the HTTP POST Request for inserting reimbursements , then send the reimbursement data to the DB. 
		public Handler insertReimbursementsHandler = (ctx) ->{
			
			//With POST requests, we have some data coming in, which we access with ctx.body();
			//body?? it refers to the BODY of the HTTP Request (which is where the incoming data is found)
			String body = ctx.body();//store the data in a String
			
			//create a new GSON object to make JSON <-> Java conversions
			Gson gson = new Gson();
			
			//turn the incoming JSON String directly into a Reimbursement object
			//remember, fromJson() is the method that takes a JSON String and turns it into some Java object
			
			Ers_Reimbursement newReimb = gson.fromJson(body, Ers_Reimbursement.class);
			
			//we call the insertReimbursements() method to send our newly created reimbursement to the DB.
			//IF it succeeds, it'll return true since that's the return type of insertReimbursements()
			
			if(rDAO.insertReimbursements(newReimb)) {
				
				log.info("User successfully inserted the reimbursement!");  //line in relation to logging
				//return a successful status code
				ctx.status(202);//202 stands for "accepted"	
				
			
		}else {
			log.info("User was unable to insert the reimbursement!");  //line in relation to logging
			ctx.status(406);//406 stands for "Not Acceptable", AKA whatever the user sent couldn't be added to the DB			
			
		}
		
};
                                                                                            //updateStatusOfReimbursements

				//this Handler will get the HTTP PUT request to update a Reimbursement Status.
				public Handler updateReimbursementStatusHandler = (ctx) ->{
					
					//String to hold the status title (which comes in as a PATH PARAMETER)
					int status_id = Integer.parseInt(ctx.pathParam("status_id"));//pathParam() gives us the value the user sends in as a path parameter
					//in this case, our Launcher endpoint handler calls it "status", so this is what we need to call here
					
					//int to hold the new Role salary, which the user will include in the BODY of the HTTP Request
					int reimbursementStatus = Integer.parseInt(ctx.body());//we need to use parseInt here, because ctx.body() returns a String
					
					
					//if the update DAO method returns true (which means successful)..
					if(rDAO.updateStatusOfReimbursements(status_id, reimbursementStatus)) {
						
						log.info("Finance Manager successfully updated reimbursements!");  //line in relation to logging
						
						ctx.status(202);						
						
					}else {
						log.info("Finance Manager was unable to update the reimbursement(s)!");  //line in relation to logging
						ctx.status(406);
						
					}
					
					//you're not getting a reimb_id anywhere. you should send it in your request body and call it in your controller using ctx.body()
					//you can see something very similar in my P1Demo
					
				};
				
				//get All their reimbursements. This is  used by the employee -> to get Reimbursements By Their User Id
				//This Handler will get the HTTP GET Request for all employees(get Reimbursements By Their User Id), then collect the data and send
				//it back in an HTTP Response 
				
				public Handler getReimbursementsByUserIdHandler = (ctx) ->{
					
					
					//ctx is the Context object that contains a bunch of methods that we can use to take in HTTP Requests and send HTTP Responses
					if(AuthController.ses != null) {//if the user is logged in, they can access this functionality
						int user_id = Integer.parseInt(ctx.pathParam("user_id"));//pathParam() gives us the value the user sends in as a path parameter
						
					//We need an ArrayList of Reimbursements	
					ArrayList<Ers_Reimbursement> reimbursements = rDAO.getReimbursementsByUserId(user_id);
					
					//create a GSON object, which has methods to convert our Java object into JSON
					Gson gson = new Gson();
					
					//use the GSON .toJson() method to turn our Java into JSON String (JSON is always in String format on the Java side)
					String JSONreimbursements = gson.toJson(reimbursements); //reimbursement is the ArrayList of our reimbursement data
					
					log.info("User was able to view all their reimbursement(s)!");  //line in relation to logging
					
					//use ctx to provide an HTTP response containing our JSON string of reimbursements (which is what was requested)					
					ctx.result(JSONreimbursements);//ctx.result() sends a response back (this is where our data goes)
					
					ctx.status(200);//ctx.status() sets the HTTP status code. 200 stands for "OK", the generic success code.
					
										
				}else {//if the user is NOT logged in (aka AuthController.ses will be null)
					log.info("User was unable to view all their reimbursement(s)!");  //line in relation to logging
					
					ctx.result("YOU ARE NOT LOGGED IN!!");
					ctx.status(401); //"forbidden" access code							  
					
				}
					
					
				};
				
				//Get all reimbursements by status by the Finance Managers
				//This Handler will get the HTTP GET Request for all Reimbursements By Their Status), then collect the data and send
				//it back in an HTTP Response
				
				public Handler getReimbursementsByStatusHandler = (ctx) ->{
					
					//what is ctx? it's the Context object! 
					//This object contains a bunch of method that we can use to take in HTTP Requests and send HTTP Responses
					
					if(AuthController.ses != null) {//if the user is logged in, they can access this functionality
						int reimb_status = Integer.parseInt(ctx.pathParam("reimb_status"));//pathParam() gives us the value the user sends in as a path parameter
						
						//We need an ArrayList of Ers_Reimbursement, courtesy of our Ers_ReimbursementDAO
						ArrayList<Ers_Reimbursement> reimbursements = rDAO.getReimbursementsByStatus(reimb_status);
						
						//create a GSON object, which has methods to convert our Java object into JSON
						Gson gson = new Gson();
						
						//use the GSON .toJson() method to turn our Java into JSON String (JSON is always in String format on the Java side)
						String JSONreimbursements = gson.toJson(reimbursements);//reimbursement is the ArrayList of our reimbursement data
						
						log.info("Finance Manager has been able to view all reimbursements by users' Reimbursement Status!");  //line in relation to logging
						
						//use ctx to provide an HTTP response containing our JSON string of employees (which is what was requested)
						ctx.result(JSONreimbursements);//ctx.result() sends a response back (this is where our data goes)
						
						ctx.status(200); //ctx.status() sets the HTTP status code. 200 stands for "OK", the generic success code.
 						
					}else {//if the user is NOT logged in (aka AuthController.ses wil be null)
						ctx.result("YOU ARE NOT LOGGED IN!!");
						ctx.status(401);//"forbidden" access code
						log.info("Finance Manager has not been able to view all reimbursements by users' Reimbursement Status!");  //line in relation to logging
						
					}
				};
				
				
				
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	
	
}
