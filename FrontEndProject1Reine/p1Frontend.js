const url = "http://localhost:4000"; //putting our base URL in a variable for cleaner code below
//This will be used in fetch requests to make requests to our java server.

//add an event listener to give our buttons some functionality (DOM Selection)
document.getElementById("getReimbursementButton").onclick = getReimbursements //this button will GET all reimbursements
document.getElementById("loginButton").onclick = loginFunction //this button will send a user's login credentials
document.getElementById("updateButton").onclick = updateReimbursementStatus //this button will send data that updates a reimbursement status

document.getElementById("getReimbursementByStatusButton").onclick = getReimbursementsByStatus
document.getElementById("getReimbursementsByUserButton").onclick = getReimbursementsByUser
document.getElementById("insertReimbursementButton").onclick = insertReimbursement

var currentUser = null;
//getReimbursements is an async function which uses a fetch request to get reimbursements from our server------------------------------------------
//remember, async makes a function return a Promise object (which fetch requests return)

async function getReimbursements(){

//a fetch request for employee data from the server
//by default, fetch requests send GET requests. (see how to send others in login function below) 
let response = await fetch(url + "/reimbursements") 

 //log the response in the console just to see the response object (good for debugging)
 console.log(response)

 //control flow on the status code to determine whether or not the request was successful
 if(response.status === 200){
    document.getElementById("reimbursementBody").innerHTML = ""

    //translate the JSON we get in the response body and turn it into a JS object
   //remember, .json() is the function that takes JSON and turns it into JS.
   let data = await response.json();

   //For every reimbursement objects we get back from our fetch request, put it in the table
  //  "reimbursement" is the variable name we're giving to each piece of data returned
  for(let reimbursement of data){

     //create a table row for the incoming reimbursement
     let row = document.createElement("tr")

     //create a data cell (td) for each reimbursement field
     let cell = document.createElement("td")
     //fill the cell with the appropriate data
     cell.innerHTML = reimbursement.reimb_id
     //add the new cell to the row
     row.appendChild(cell)

     //we'll do this^^^ for each column in the reimbursement object

     //cell2 - reimb_amount
     let cell2 = document.createElement("td")
     cell2.innerHTML = reimbursement.reimb_amount
     row.appendChild(cell2)

     //cell3 - reimb_submitted
     let cell3 = document.createElement("td")
     cell3.innerHTML = reimbursement.reimb_submitted
     row.appendChild(cell3)

     //cell4 - reimb_author_fk => ers_users_username
     let cell4 = document.createElement("td")
     cell4.innerHTML = reimbursement.reimb_author_fk  //?????????????????????????????////
     row.appendChild(cell4)

     //cell5 - reimb_status_id_fk => reimb_status_id
     let cell5 = document.createElement("td")
     cell5.innerHTML = reimbursement.reimb_status_id_fk //????
     row.appendChild(cell5)

     //cell6 - reimb_type_id_fk => reimb_type_id
     let cell6 = document.createElement("td")
     cell6.innerHTML = reimbursement.reimb_type_id_fk
     row.appendChild(cell6)

     document.getElementById("reimbursementBody").appendChild(row)
  }
} else if(response.status === 401){
    alert("You are not authorized to take this action!")
 }else{
    alert("something went wrong! Did you run your Java?")
 }
} //end of getReimbursements

//This function will send the user's login credentials as JSON to the backend-------------------------------
async function loginFunction(){
    //gather the user's credentials from the login inputs
    //when the login button is clicked, these variables get populated
    let user = document.getElementById("username").value
    let pass = document.getElementById("password").value

    //we want to send this login data as JSON, so we need a JS object first
    let userCreds = {
        username: user,
        password: pass
    }//the names of the variables must match our Java object (LoginDTO in this case)

     //for debugging purposes, print out userCreds
     console.log(userCreds)

    //fetch request to the server
    //this fetch has two parameters: the URL, and a configuration object to configure our HTTP Request
    //In this config object, we're going to manipulate attributes to make sure we send a POST with a body
    let response = await fetch(url + "/login",{
        method:"POST", //send a POST request (would be a GET if we didn't specify)
        body: JSON.stringify(userCreds),//turning our userCreds object into JSON to send to the server
        credentials:"include"
        //this last line will ensure a cookie gets captured (so that we can use sessions)
        //future fetches after login will require this "include" variable************************
    })

    //log the response status code, good for debugging
    if(response.status === 202){

        //convert the incoming User data from JSON to JS (remember login returns a User)
        currentUser = await response.json();

        //wipe our login row and welcome the user
        document.getElementById("welcomeHead").innerText="Congratulations. You are logged in!";
        document.getElementById("welcomeHead").style.color = "green";

    }else{
        //
        document.getElementById("welcomeHead").innerText="Login Failed! Please try again...";
        document.getElementById("welcomeHead").style.color = "red";

    }


}
//Ben recommends to have one login page, and based on the user's user_role_id, switch pages
//so if user_role_id is equal to "employee", send them to the employees page
//and then the same kind of logic for managers.
//You'll have to google how to switch pages from a JS function. Easier than it sounds!!

//This would be 3 HTML total and 3 JS files total

//This function will take in user input to update a Reimbursement Status-------------------------------
async function updateReimbursementStatus(){
    //gather the user inputs for reimbursement status id and reimbursement status
    //remember, the values in parenthesis are the IDs of the input elements in our HTML
    let status = document.getElementById("reimbursementId").value 
    let status_id = document.getElementById("status").value

    //fetch request to update reimbursement status (PUT)
    //it will take the reimbursement status id as a path param, and it will take the new status as the body
    let response = await fetch(url + "/reimbursements/" + status_id, {
        method:"PUT", //send a PUT request (check the endpoint handler in the launcher, it takes a PUT to "/reimbursements/:status_id")
        body: status //any need to turn this into JSON, is it a STRING ???????
    })
    //this fetch has two parameters: the URL, and a {configuration object} to configure our HTTP Request
    //In this config object, we're going to manipulate attributes to make sure we send a PUT with a body

    //I want some control flow on the status code to change the HTML appropriately
    if(response.status === 202){//if the update was successful...
        //tell the user the update was successful, along with the pertinent details
        document.getElementById("updateHeader").innerText = "Reimbursement status for reimbursement " + status + " updated to: " + status_id
        // ABOVE I think instead of status_id PUT REIMB_ID ?????????????????????
    } else if(response.status === 401){
        alert("You are not authorized to take this action!")
        
    }else{
        document.getElementById("updateHeader").innerText = "Update failed! Check the status id you typed or ?"
        document.getElementById("updateHeader").style.color = "red";
    }
    //?????????????// Do I append a table here??????


}// end of updateReimbursementStatus

async function getReimbursementsByStatus(){

    let status_id = document.getElementById("statusInput").value

    //a fetch request for reimbursement data from the server
    //by default, fetch requests send GET requests. (see how to send others in login function ) 
    let response = await fetch(url + "/reimbursementsByStatus/" + status_id) 
    
     //log the response in the console just to see the response object (good for debugging)
     console.log(response)
    
     //control flow on the status code to determine whether or not the request was successful
     if(response.status === 200){
        document.getElementById("reimbursementBody").innerHTML = ""
    
        //translate the JSON we get in the response body and turn it into a JS object
       //remember, .json() is the function that takes JSON and turns it into JS.
       let data = await response.json();
    
       //For every reimbursement objects we get back from our fetch request, put it in the table
      //  "reimbursement" is the variable name we're giving to each piece of data returned
      for(let reimbursement of data){
    
         //create a table row for the incoming reimbursement
         let row = document.createElement("tr")
    
         //create a data cell (td) for each reimbursement field
         let cell = document.createElement("td")
         //fill the cell with the appropriate data
         cell.innerHTML = reimbursement.reimb_id
         //add the new cell to the row
         row.appendChild(cell)
    
         //we'll do this^^^ for each column in the reimbursement object
    
         //cell2 - reimb_amount
         let cell2 = document.createElement("td")
         cell2.innerHTML = reimbursement.reimb_amount
         row.appendChild(cell2)
    
         //cell3 - reimb_submitted
         let cell3 = document.createElement("td")
         cell3.innerHTML = reimbursement.reimb_submitted
         row.appendChild(cell3)
    
         //cell4 - reimb_author_fk => ers_users_username
         let cell4 = document.createElement("td")
         cell4.innerHTML = reimbursement.reimb_author_fk  //?????????????????????????????////
         row.appendChild(cell4)
    
         //cell5 - reimb_status_id_fk => reimb_status_id
         let cell5 = document.createElement("td")
         cell5.innerHTML = reimbursement.reimb_status_id_fk //????
         row.appendChild(cell5)
    
         //cell6 - reimb_type_id_fk => reimb_type_id
         let cell6 = document.createElement("td")
         cell6.innerHTML = reimbursement.reimb_type_id_fk
         row.appendChild(cell6)
    
         document.getElementById("reimbursementBody").appendChild(row)
    
    
      }
    } else if(response.status === 401){
        alert("You are not authorized to take this action!")
     }else{
        alert("something went wrong! Did you run your Java?")
     }
    } //end of getReimbursementsByStatus(status_id)

    
    // getReimbursementsByUser()
    async function getReimbursementsByUser(){

        let user_id = document.getElementById("userIdInput").value
    
        //a fetch request for reimbursement data from the server
        //by default, fetch requests send GET requests. (see how to send others in login function ) 
        let response = await fetch(url + "/reimbursementsByUser/" + user_id) 
        
         //log the response in the console just to see the response object (good for debugging)
         console.log(response)
        
         //control flow on the status code to determine whether or not the request was successful
         if(response.status === 200){
            document.getElementById("reimbursementBody").innerHTML = ""
        
            //translate the JSON we get in the response body and turn it into a JS object
           //remember, .json() is the function that takes JSON and turns it into JS.
           let data = await response.json();
        
           //For every reimbursement objects we get back from our fetch request, put it in the table
          //  "reimbursement" is the variable name we're giving to each piece of data returned
          for(let reimbursement of data){
        
             //create a table row for the incoming reimbursement
             let row = document.createElement("tr")
        
             //create a data cell (td) for each reimbursement field
             let cell = document.createElement("td")               //DO WE NEED THIS CELL ??????
             //fill the cell with the appropriate data
             cell.innerHTML = reimbursement.reimb_id
             //add the new cell to the row
             row.appendChild(cell)
        
             //we'll do this^^^ for each column in the reimbursement object
        
             //cell2 - reimb_amount
             let cell2 = document.createElement("td")
             cell2.innerHTML = reimbursement.reimb_amount
             row.appendChild(cell2)
        
             //cell3 - reimb_submitted
             let cell3 = document.createElement("td")
             cell3.innerHTML = reimbursement.reimb_submitted
             row.appendChild(cell3)
        
             //cell4 - reimb_author_fk => ers_users_username
             let cell4 = document.createElement("td")
             cell4.innerHTML = reimbursement.reimb_author_fk  
             row.appendChild(cell4)
        
             //cell5 - reimb_status_id_fk => reimb_status_id
             let cell5 = document.createElement("td")
             cell5.innerHTML = reimbursement.reimb_status_id_fk //????
             row.appendChild(cell5)
        
             //cell6 - reimb_type_id_fk => reimb_type_id
             let cell6 = document.createElement("td")
             cell6.innerHTML = reimbursement.reimb_type_id_fk
             row.appendChild(cell6)
        
             document.getElementById("reimbursementBody").appendChild(row)  // Am I to append the same reimbursementBody???
                                                                           // as I did for GET ALL REIMBURSEMENTS?
        
        
          }
        } else if(response.status === 401){
            alert("You are not authorized to take this action!")
         }else{
            alert("something went wrong! Did you put the wrong User Id perhaps?")
         }
        } //end of getReimbursementsByUser(user_id)

        

        // insertReimbursement()
        async function insertReimbursement(){
            //gather the user's credentials from the login inputs
    //when the login button is clicked, these variables get populated
    let type = document.getElementById("typeInput").value
    let amount = document.getElementById("amountInput").value

    //we want to send this login data as JSON, so we need a JS object first
    let userCreds = {
    reimb_amount: amount,	
	reimb_author_fk:currentUser.ers_users_id,
    reimb_submitted:"07/29/2022",
	reimb_status_id_fk:2,
	reimb_type_id_fk: type
    }//the names of the variables must match our Java object (LoginDTO in this case)

     //for debugging purposes, print out userCreds
     console.log(userCreds)

    //fetch request to the server
    //this fetch has two parameters: the URL, and a configuration object to configure our HTTP Request
    //In this config object, we're going to manipulate attributes to make sure we send a POST with a body
    let response = await fetch(url + "/reimbursements",{
        method:"POST", //send a POST request (would be a GET if we didn't specify)
        body: JSON.stringify(userCreds),//turning our userCreds object into JSON to send to the server
        credentials:"include"
        //this last line will ensure a cookie gets captured (so that we can use sessions)
        //future fetches after login will require this "include" variable************************
      })
      if(response.status === 202){

        //convert the incoming User data from JSON to JS (remember login returns a User)
        
        //wipe our login row and welcome the user
        document.getElementById("insertHeader").innerText="Congratulations. You submitted a reimbursement!";
        document.getElementById("insertHeader").style.color = "blue";

        } else if(response.status === 401){
            alert("You are not authorized to take this action!")
         }else{
            alert("something went wrong! Did you put the wrong User Id perhaps?")
         }
        }

         //

        








//*******************************
//reimb_id SERIAL PRIMARY KEY,
//reimb_amount INT,
//reimb_submitted TEXT,
//reimb_author_fk INT REFERENCES ers_users (ers_users_id),
//reimb_status_id_fk INT REFERENCES reimbursement_status (reimb_status_id),
//reimb_type_id_fk INT REFERENCES reimbursement_type (reimb_type_id)


