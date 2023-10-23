-- Create the Merchant table
CREATE TABLE merchant (
                           id INT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           contact_info TEXT
);

CREATE SEQUENCE merchant_seq START WITH 1 INCREMENT BY 1;

-- Create the Customers table
CREATE TABLE customer (
                           id INT PRIMARY KEY,
                           first_name VARCHAR(255) NOT NULL,
                           last_name VARCHAR(255) NOT NULL,
                           contact_info TEXT
);

CREATE SEQUENCE customer_seq START WITH 1 INCREMENT BY 1;

-- Create the Products table
CREATE TABLE product (
                          id INT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          category VARCHAR(50) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          merchant_id INT,
                          FOREIGN KEY (merchant_id) REFERENCES Merchant(id)
);

CREATE SEQUENCE product_seq START WITH 1 INCREMENT BY 1;

-- Create the Transactions table
CREATE TABLE transaction (
                              id INT PRIMARY KEY,
                              customer_id INT,
                              transaction_date DATE  NOT NULL,
                              transaction_time TIME NOT NULL,
                              total_amount DECIMAL(10, 2) NOT NULL,
                              FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

CREATE SEQUENCE transaction_seq START WITH 1 INCREMENT BY 1;

-- Create the TransactionDetails table
CREATE TABLE transaction_detail (
                                      id INT PRIMARY KEY,
                                      transaction_id INT,
                                      product_id INT,
                                      quantity INT NOT NULL,
                                      sub_total DECIMAL(10, 2) NOT NULL,
                                      FOREIGN KEY (transaction_id) REFERENCES Transaction(id),
                                      FOREIGN KEY (product_id) REFERENCES Product(id)
);

CREATE SEQUENCE transaction_detail_seq START WITH 1 INCREMENT BY 1;