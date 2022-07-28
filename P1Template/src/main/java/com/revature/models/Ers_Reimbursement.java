package com.revature.models;

public class Ers_Reimbursement {
	
	private int reimb_id;
	private int reimb_amount;
	private String reimb_submitted;
	private int reimb_author_fk;
	private int reimb_status_id_fk;
	private int reimb_type_id_fk;
	
	
	//a no -args constructor is used for default values !!!!!
	public Ers_Reimbursement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	// ALL args constructor    
	public Ers_Reimbursement(int reimb_id, int reimb_amount, String reimb_submitted, int reimb_author_fk,
			int reimb_status_id_fk, int reimb_type_id_fk) {
		super();
		this.reimb_id = reimb_id;
		this.reimb_amount = reimb_amount;
		this.reimb_submitted = reimb_submitted;
		this.reimb_author_fk = reimb_author_fk;
		this.reimb_status_id_fk = reimb_status_id_fk;
		this.reimb_type_id_fk = reimb_type_id_fk;
	}
	
	//ALL- ARGS constructor with NO ID


	

	public Ers_Reimbursement(int reimb_amount, String reimb_submitted, int reimb_author_fk, int reimb_status_id_fk,
			int reimb_type_id_fk) {
		super();
		this.reimb_amount = reimb_amount;
		this.reimb_submitted = reimb_submitted;
		this.reimb_author_fk = reimb_author_fk;
		this.reimb_status_id_fk = reimb_status_id_fk;
		this.reimb_type_id_fk = reimb_type_id_fk;
	}

	public int getReimb_id() {
		return reimb_id;
	}

	public void setReimb_id(int reimb_id) {
		this.reimb_id = reimb_id;
	}

	public int getReimb_amount() {
		return reimb_amount;
	}

	public void setReimb_amount(int reimb_amount) {
		this.reimb_amount = reimb_amount;
	}

	public String getReimb_submitted() {
		return reimb_submitted;
	}

	public void setReimb_submitted(String reimb_submitted) {
		this.reimb_submitted = reimb_submitted;
	}

	public int getReimb_author_fk() {
		return reimb_author_fk;
	}

	public void setReimb_author_fk(int reimb_author_fk) {
		this.reimb_author_fk = reimb_author_fk;
	}

	public int getReimb_status_id_fk() {
		return reimb_status_id_fk;
	}

	public void setReimb_status_id_fk(int reimb_status_id_fk) {
		this.reimb_status_id_fk = reimb_status_id_fk;
	}

	public int getReimb_type_id_fk() {
		return reimb_type_id_fk;
	}

	public void setReimb_type_id_fk(int reimb_type_id_fk) {
		this.reimb_type_id_fk = reimb_type_id_fk;
	}

	@Override
	public String toString() {
		return "Ers_Reimbursement [reimb_id=" + reimb_id + ", reimb_amount=" + reimb_amount + ", reimb_submitted="
				+ reimb_submitted + ", reimb_author_fk=" + reimb_author_fk + ", reimb_status_id_fk="
				+ reimb_status_id_fk + ", reimb_type_id_fk=" + reimb_type_id_fk + "]";
	}
	
	
	
	
	
	
	
	
	

}
