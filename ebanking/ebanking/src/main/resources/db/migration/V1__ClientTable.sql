    CREATE TABLE Client(
        id SERIAL PRIMARY KEY,
        email VARCHAR(30) NOT NULL,
        firstname VARCHAR(30) NOT NULL,
        lastname VARCHAR(30) NOT NULL,
        username VARCHAR(30) NOT NULL,
        password VARCHAR(30) NOT NULL,
        admin INT NOT NULL,
        jwt VARCHAR(130)
        );

    CREATE TABLE Account(
    	id SERIAL PRIMARY KEY,
    	client_id INT NOT NULL,
    	account_no INT NOT NULL,
    	bank VARCHAR(30) NOT NULL,
    	balance REAL NOT NULL,
    	state INT NOT NULL
        );

    CREATE TABLE Transaction(
    	id SERIAL PRIMARY KEY,
    	dest_account INT NOT NULL,
    	source_account INT NOT NULL,
    	source_balance REAL NOT NULL,
    	dest_balance REAL NOT NULL,
    	amount REAL NOT NULL,
    	date TIMESTAMP NOT NULL
        );