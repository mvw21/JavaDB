CREATE DATABASE SoftUni;

Use SoftUni;

CREATE TABLE `towns`(
`id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(55)
);


CREATE TABLE `addresses` (
`id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`address_text` VARCHAR(55), 
`town_id` INT(11),
CONSTRAINT fk_id foreign key (town_id) REFERENCES towns(id)
);

CREATE TABLE `departments` (
`id` INT(11) PRIMARY KEY AUTO_INCREMENT, 
`name` VARCHAR(55)
);

CREATE TABLE `employees` (
`id` INT(11) PRIMARY KEY AUTO_INCREMENT, 
`first_name` VARCHAR(55), 
`middle_name` VARCHAR(55), 
`last_name` VARCHAR(55), 
`job_title` VARCHAR(100), 
`department_id` INT(11), CONSTRAINT fk_dp FOREIGN KEY (department_id) REFERENCES departments(id),
`hire_date` DATE,
`salary` DECIMAL,
`address_id` INT(11),CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES addresses(id)
);
