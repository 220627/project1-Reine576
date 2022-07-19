package com.revature;

import io.javalin.Javalin;

public class Launcher {

	public static void main(String[] args) {
		
		//Welcome to P1! 
		
		//If you're reading this, you've successfully cloned your repo and imported the template
		
		//Do your coding in this project, and don't forget to save/push your progress with:
		//git add.
		//git commit -m"message"
		//git push
		
		//yes, you WILL need to push to your repo. The clients will want to see your project repos in your portfolios.
		
		//here's a dog to help you on your way. Have fun!
		
//               __
//          (___()'`;
//          /,    /`
//          \\"--\\
       //CREATE a javalin object 
		Javalin app = Javalin.create(
				
				//the config lambda 
				config -> {
					config.enableCorsForAllOrigins(); //this lets us process HTTP requests from any origin
				}
				
				).start(4000); // port 4000 where requests are sent to.
		
	}
	
}
