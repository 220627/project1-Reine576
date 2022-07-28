--Welcome to Employment Reimbursement System (ERS)

CREATE TABLE reimbursement_status(

reimb_status_id SERIAL PRIMARY KEY,
reimb_status TEXT

);

INSERT INTO reimbursement_status(reimb_status)
VALUES ('Submitted'),('Pending'),('Approved');

SELECT * FROM reimbursement_status;

CREATE TABLE reimbursement_type(

reimb_type_id SERIAL PRIMARY KEY,
reimb_type TEXT

);

INSERT INTO reimbursement_type(reimb_type)
VALUES('Lodging'),('Travel'),('Food'),('Other');


SELECT * FROM reimbursement_type;

CREATE TABLE user_roles(
ers_user_role_id SERIAL PRIMARY KEY,
user_role TEXT

);

INSERT INTO user_roles(user_role)
VALUES('Employee'),('Finance Manager');


SELECT * FROM user_roles;

CREATE TABLE ers_users(
ers_users_id SERIAL PRIMARY KEY,
ers_username TEXT UNIQUE,
ers_password TEXT,
user_first_name TEXT,
user_last_name TEXT,
user_email TEXT UNIQUE,
user_role_id_fk INT REFERENCES user_roles(ers_user_role_id)

); 

INSERT INTO ers_users(ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id_fk)
VALUES('Charly','chacha1', 'Charles', 'Canjump', 'charlychap@gmail.com', 2),
	  ('Fort','fofo', 'Fortune', 'Fehomy', 'fortune@gmail.com', 1),
	  ('Pat','pat1', 'Patricia', 'Powerjack', 'patricia@yahoo.com', 2),
	  ('Martino','mamasboy', 'Martin', 'Maker', 'martin@gmail.com', 1),
	  ('Henco','henco1', 'Henry', 'Highlander', 'henry@gmail.com', 2);
	 

SELECT * FROM ers_users;

DROP TABLE ers_users

CREATE TABLE ers_reimbursement(

reimb_id SERIAL PRIMARY KEY,
reimb_amount INT,
reimb_submitted TEXT,
reimb_author_fk INT REFERENCES ers_users (ers_users_id),
reimb_status_id_fk INT REFERENCES reimbursement_status (reimb_status_id),
reimb_type_id_fk INT REFERENCES reimbursement_type (reimb_type_id)
);

INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_author_fk, reimb_status_id_fk, reimb_type_id_fk)
VALUES (108600,'06/30/2022',2,1,2),
	   (88800, '06/25/2022',4,1,3);
	  

SELECT * FROM ers_reimbursement;












