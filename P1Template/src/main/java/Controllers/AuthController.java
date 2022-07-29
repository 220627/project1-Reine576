package Controllers;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.revature.models.Ers_Users;
import com.revature.models.LoginDTO;
import com.revature.services.AuthService;

import io.javalin.http.Handler;
//import jdk.internal.org.jline.utils.Log;

public class AuthController {
	
	public static Logger log = LogManager.getLogger(); //for the log. Import the apache log...
	//we need an AuthService, remember the Service layer sits between the Controllers and DAOs
	AuthService as = new AuthService();
	
	//empty HttpSession object that will be filled upon successful login
	public static HttpSession ses;
	
	//we need a loginHandler to take in Login Data, make a LoginDTO, and send it to the service
	//if login is successful, the service sends back a String which we use in our return (ctx.result())
	//we test whether the login service method returns null or a String 
	//also, a Session will be created when the user logs in successfully.
		
	//login will be a POST request, check insertReimbursementsHandler if any of this syntax eludes you
	public Handler loginHandler = (ctx) -> { //javalin related import is what I did
		
		String body = ctx.body();
		
		Gson gson = new Gson();  
		
		LoginDTO lDTO = gson.fromJson(body, LoginDTO.class);
		
		Ers_Users user = as.login(lDTO.getUsername(), lDTO.getPassword()); //this will either be a User or Null.
		
		if(user != null) {//if login is successful...
			//log.info("User Logged In!"); Add this later when log is added
			
			ses = ctx.req.getSession(); //this is how we create new sessions
			
			String userJSON = gson.toJson(user); //turn the returned User into JSON
			
			log.info("User was able to login!");  //line in relation to logging
			
			ctx.result(userJSON); //send the user to the front end
			
			ctx.status(202);   //202 stands for "accepted"
			
		}else {
			log.info("User was unable to login!");  //line in relation to logging
			//log that the user failed to log in
			//log.warn("User Failed to Login!"); Add this when I add log
			
			ctx.status(401); // 401 stands for "unauthorized"
			
			
		}
	};//end of handler	
	

}
