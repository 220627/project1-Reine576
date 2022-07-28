package com.revature.daos;

import java.util.ArrayList;

import com.revature.models.Ers_Reimbursement;

public interface Ers_ReimbursementDAOInterface {
	
	//Method to get All their reimbursements. This is  used by the employee
	public ArrayList<Ers_Reimbursement> getReimbursementsByUserId(int ers_users_id);
	
	
	//Method to insert new reimbursements. This is used by the employee
	public boolean insertReimbursements(Ers_Reimbursement r);
	
	
	//Method to Get all reimbursements. This is used by the Finance Managers
	public ArrayList<Ers_Reimbursement> getReimbursements();
	
	//Method to Get all reimbursements by status by the Finance Managers
	public ArrayList<Ers_Reimbursement> getReimbursementsByStatus(int status_id);
	
	
	//Method Update status of reimbursements used by Finance Managers
	public boolean updateStatusOfReimbursements( int status_id, int reimb_id);  // i had put in parameters (int user_id, String status) but it wasnt best?
	                                                              //will it show the status itself ex Submitted, Pending, Approved???

	
}
