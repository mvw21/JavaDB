/*01.Create Tables*/
CREATE TABLE employees(
id INT(11) PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(45) NOT NULL,
last_name VARCHAR(45) NOT NULL
);
CREATE TABLE categories (
id INT(11) PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(45) NOT NULL
);
CREATE TABLE products(
id INT(11) PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(45) NOT NULL,
category_id VARCHAR(45) NOT NULL
);

/*02.Insert info*/
INSERT INTO employees (first_name,last_name) VALUES ('Pesho','Peshov');
INSERT INTO employees (first_name,last_name) VALUES ('Gosho','Goshov');
INSERT INTO employees (first_name,last_name) VALUES ('Tosho','Toshov');

/*03.Alter tables*/
ALTER TABLE employees
ADD middle_name VARCHAR(255);

/*04.Adding Constraints*/
ALTER TABLE products
ADD CONSTRAINT FK_category_id
FOREIGN KEY (category_id) REFERENCES categories(id);

/*05.Modifying Columns*/
ALTER TABLE employees
MODIFY COLUMN middle_name VARCHAR(100);
