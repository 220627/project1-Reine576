package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.models.Ers_Reimbursement;
import com.revature.utils.ConnectionUtil;

public class Ers_ReimbursementDAO implements Ers_ReimbursementDAOInterface{
	
	
	//Method to get All their reimbursements. This is  used by the employee -> to get Reimbursements By Their User Id
	@Override
	public ArrayList<Ers_Reimbursement> getReimbursementsByUserId(int ers_users_id) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			//A String that will represent our SQL statement
			String sql = "SELECT * from ers_reimbursement where reimb_author_fk =? ";
					
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setInt(1, ers_users_id);
					
					ResultSet rs = ps.executeQuery();
					
					//Instantiate an empty ArrayList to hold our Ers_Reimbursement data
					ArrayList<Ers_Reimbursement> reimbursementList = new ArrayList<>();
					
					//use rs.next() in a while loop to create Ers_Reimbursement object and populate our ArrayList with them.
					while(rs.next()) {
						
						//Create a new Ers_Reimbursement object from each record in the ResultSet
						//we're using the all-args constructor!!
						
						Ers_Reimbursement e = new Ers_Reimbursement(
								
								rs.getInt("reimb_id"),
							    rs.getInt("reimb_amount"),
							    rs.getString("reimb_submitted"),
							    rs.getInt("reimb_author_fk"),
							    rs.getInt("reimb_status_id_fk"),
							    rs.getInt("reimb_type_id_fk")
							    						
								);
					//add the new reimbursement to our ArrayList. For every reimbursement returned,
					reimbursementList.add(e);
						
					}//end of while loop
					return reimbursementList;
				} catch(SQLException e)	{
					System.out.println("SOMETHING WENT WRONG GETTING YOUR REIMBURSEMENT LIST");
					e.printStackTrace();
				}
				return null;	
					
		
	}
	
	//Method to insert new reimbursements. This is used by the employee
	@Override
	public boolean insertReimbursements(Ers_Reimbursement r) {
		
		//at the top of every single DAO method, we need to open a database connection.
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			//String to 
			String sql = "INSERT into  ers_reimbursement (reimb_amount, reimb_submitted, reimb_author_fk, reimb_status_id_fk, reimb_type_id_fk) "
					+ "values (?,?,?,?,?); ";
			
			//Instantiate a PreparedStatement to fill in the variables of our SQL String (the ?s).
			//we use the prepareStatement() method from our Connection object to do this.
			PreparedStatement ps = conn.prepareStatement(sql);
			
			//fill in the values of our variables using ps.setXYZ()
			//these methods take two parameters - the variable we'll filling, and the value to fill it with
			ps.setInt(1, r.getReimb_amount());
			ps.setString(2, r.getReimb_submitted());
			ps.setInt(3, r.getReimb_author_fk());
			ps.setInt(4, r.getReimb_status_id_fk());
			ps.setInt(5, r.getReimb_type_id_fk());
			
			System.out.println(ps);
			System.out.println(" Your Reimbursement was added !");
			ps.executeUpdate();
			
			return true;	
			
		}catch(SQLException e) {
			System.out.println(" YOUR REIMBURSEMENT INSERTION FAILED, TRY AGAIN");
			e.printStackTrace();
		}		
		return false;
	}
	
	
	
	//Method to Get all reimbursements. This is used by the Finance Managers
	@Override
	public ArrayList<Ers_Reimbursement> getReimbursements() {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			//A String that will represent our SQL statement
			String sql = "SELECT * from ers_reimbursement; ";
			
			//no variables so we don't need a PreparedStatement!
			//What we'll use instead is a Statement object to execute our query
			Statement s = conn.createStatement();
			
			//Execute query into a ResultSet object, which will hold the incoming data
			ResultSet rs = s.executeQuery(sql);
			
			//Instantiate an empty ArrayList to hold our Ers_Reimbursement data
			ArrayList<Ers_Reimbursement> reimbursementList = new ArrayList<>();
			
			//use rs.next() in a while loop to create Ers_Reimbursement object and populate our ArrayList with them.
			while(rs.next()) {
				
				//Create a new Ers_Reimbursement object from each record in the ResultSet
				//we're using the all-args constructor!!
				
				Ers_Reimbursement e = new Ers_Reimbursement(
						
						rs.getInt("reimb_id"),
					    rs.getInt("reimb_amount"),
					    rs.getString("reimb_submitted"),
					    rs.getInt("reimb_author_fk"),
					    rs.getInt("reimb_status_id_fk"),
					    rs.getInt("reimb_type_id_fk")
					    						
						);
				//add the new reimbursement to our ArrayList. For every employee returned,
				reimbursementList.add(e);
				
			}//end of while loop
			return reimbursementList;
		} catch(SQLException e)	{
			System.out.println("SOMETHING WENT WRONG GETTING REIMBURSEMENT LIST");
			e.printStackTrace();
		}
		return null;
		
	}
	
	//Method to Get all reimbursements by status by the Finance Managers
	@Override
	public ArrayList<Ers_Reimbursement> getReimbursementsByStatus(int status_id) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			//A String that will represent our SQL statement
			String sql = "SELECT * from ers_reimbursement where reimb_status_id_fk =?;";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, status_id);  
			ResultSet rs = ps.executeQuery();
			
			//Instantiate an empty ArrayList to hold our Ers_Reimbursement data
			ArrayList <Ers_Reimbursement> reimbursementList = new ArrayList<>();
			
			//use rs.next() in a while loop to create Ers_Reimbursement object and populate our ArrayList with them.
			while(rs.next()) {
				
				//Create a new Ers_Reimbursement object from each record in the ResultSet
				//we're using the all-args constructor!!
				
				Ers_Reimbursement e = new Ers_Reimbursement(
						
						rs.getInt("reimb_id"),
					    rs.getInt("reimb_amount"),
					    rs.getString("reimb_submitted"),
					    rs.getInt("reimb_author_fk"),
					    rs.getInt("reimb_status_id_fk"),
					    rs.getInt("reimb_type_id_fk")
					    						
						);
				//add the new reimbursement to our ArrayList. For every reimbursement returned,
				reimbursementList.add(e); 	
			
		}//end of while loop		
		return reimbursementList;
		
	}catch(SQLException e) {
		System.out.println("OOPs, SOMETHING WENT WRONG, try again!......");
		e.printStackTrace();
	}
	
		return null;
		
	}
	
	//Method Update status of reimbursements used by Finance Managers
	@Override
	public boolean updateStatusOfReimbursements(int status_id, int reimb_id) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "UPDATE ers_reimbursement set reimb_status_id_fk =? where reimb_id =?;"; //I had written this wrong statement =>String sql = "UPDATE ers_reimbursement set reimb_status =? where reimb_id =?;";
			//create our PreparedStatement to fill in the variables
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1,status_id);
			ps.setInt(2, reimb_id);
			
			//execute the update
			ps.executeUpdate();
			
			System.out.println("All Reimbursement status have been updated! ");
			
			return true;		
			
		}catch(SQLException e) {
			System.out.println("FAILED TO UPDATE!");
			e.printStackTrace();
		}		
		return false;
	}
	
	

}
