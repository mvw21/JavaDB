CREATE DATABASE car_rental;

USE car_rental;

CREATE TABLE `categories`(
`id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`category` VARCHAR(255) NOT NULL,
`daily_rate` FLOAT,
`weekly_rate` FLOAT,
`monthly_rate` FLOAT,
`weekend_rate` FLOAT
);

CREATE TABLE `cars`(
`id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`plate_number` VARCHAR(100),
`make` VARCHAR(50),
`model` VARCHAR(50),
`car_year` YEAR(4),   /*MAYBE*/
`category_id` INT(11),
`doors` INT(11),
`picture` BLOB,
`car_condition` VARCHAR(45),
`available` BOOLEAN
);

CREATE TABLE `employees`(
`id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(50),
`last_name` VARCHAR(50),
`title` VARCHAR(255),
`notes` TEXT
);

CREATE TABLE `customers` (
`id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`driver_licence_number` VARCHAR(50), /*MAYBE INT*/
`full_name` VARCHAR(100),
`address` VARCHAR(100),
`city` VARCHAR(100),
`zip_code` INT(11),
`notes` TEXT
);

CREATE TABLE `rental_orders` (
`id` INT(11) PRIMARY KEY AUTO_INCREMENT ,
`employee_id` INT(11),
`customer_id`INT(11),
`car_id` INT(11),
`car_condition` VARCHAR(100),
`tank_level` INT(11),
`kilometrage_start` INT(11),
`kilometrage_end` INT(11),
`total_kilometrage` INT(11),
`start_date` DATE,
`end_date` DATE,
`total_days` INT(11),
`rate_applied` INT(11),
`tax_rate` INT(11),
`order_status` VARCHAR(50),
`notes` TEXT
);

INSERT INTO `categories` (`category`,`daily_rate`,`weekly_rate`,`monthly_rate`,`weekend_rate`) VALUES
('One',1.1,2.2,3.3,4.4),
('Two',5.5,6.6,7.7,8.8),
('Three',1.1,2.2,3.3,5.5);

INSERT INTO `cars` (`plate_number`,`make`,`model`,`car_year`,`category_id`,`doors`,`picture`,`car_condition`,`available`) 
VALUES
('A1111SV','VOLVO','TEST',1988,12,4,NULL,'Good',True), /* 1/0*/
('B2345AC','Mercedes','Benz',1998,12,4,NULL,'Amazing',False),
('C3232AB','Skoda','Octavia',2018,22,4,NULL,'Very Good',True);

INSERT INTO `employees` (`first_name`,`last_name`,`title`,`notes`) 
VALUES 
('Ivan','Ivanov','Worker','texteteas asfaf afaf'),
('Pesho','Peshov','Boss','teasafafd asd asd '),
('Gosho','Goshov','Rapper','asddgdfg');

INSERT INTO `customers` (`driver_licence_number`,`full_name`,`address`,`city`,`zip_code`,`notes`)
VALUES
('1234','Gosho Goshov','USA','NY',9877,'asasddas'),
('1235','Pesho Goshov','Canada','Vancoover',9545,'asaasdadsddas'),
('1232','Sasho Goshov','Mexico','MexicoCity',9444,'asaddasddas');

INSERT INTO `rental_orders` (`employee_id`,
`customer_id`,`car_id`,`car_condition`,`tank_level`,`kilometrage_start`,`kilometrage_end`,`total_kilometrage`,`start_date`,`end_date`,`total_days`,`rate_applied`,`tax_rate`,`order_status`,`notes`)
VALUES
(23,33,44,'Good',123,1112,2222,1234,'1992-02-00','1993-02-00',122,12,13,'some status','test text'),
(24,34,45,'Bad',124,1113,2223,1234,'1989-02-00','1994-02-00',122,12,13,'some status','test text'),
(22,35,46,'Very Good',125,1114,2224,1234,'1999-02-00','2000-02-00',122,12,13,'some status','test text');
