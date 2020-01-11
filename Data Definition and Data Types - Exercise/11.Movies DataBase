CREATE DATABASE `movies`;

USE `movies`;

CREATE TABLE `directors`(
id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
director_name VARCHAR(50) NOT NULL,
notes TEXT
);

CREATE TABLE `genres`(
id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
`genre_name` VARCHAR(50) NOT NULL,
`notes` TEXT
);

CREATE TABLE `categories`(
id INT(11) PRIMARY KEY AUTO_INCREMENT,
`category_name` VARCHAR(50) NOT NULL,
`notes` TEXT
);

CREATE TABLE `movies`(
id INT(11) PRIMARY KEY AUTO_INCREMENT,
`title` TEXT NOT NULL,
`director_id` INT(11) NOT NULL,
`copyright_year` YEAR(4) NOT NULL,
`length` TIME NOT NULL,
`genre_id` INT(11) NOT NULL, 
`category_id` INT(11) NOT NULL,
`rating` DOUBLE NOT NULL,
`notes` TEXT
);

INSERT INTO `directors`(director_name,notes)
VALUES('Tarantino','Once upon a time'),
('GP','Gosho Ot Pochivka'),
('Dana White','UFC'),
('Spielberg',NULL),
('TESTDIRECTOR','TESTMOVIE');

INSERT INTO `genres`(genre_name,notes)
VALUES('WESTERN','BEST'),
('Comedy','Two and a half men'),
('SAGA','Worst'),
('SW9',NULL),
('HORROR','BFS');

INSERT INTO `categories`(category_name,notes)
VALUES('Test1','notes1234'),
('Test2',NULL),
('Test3','randomtext'),
('Test4','testtest'),
('Test5','teafdf3434');

INSERT INTO `movies`(title, director_id, copyright_year, length, genre_id, category_id, rating, notes)
VALUES
('Once upon a time',2,2000,'120:00:00',2,4,8.5,'blabla'),
('Twice upon a time',3,2001,'70:00:00',2,4,8.5,'basdasdasdlabla'),
('SW',2,2002,4,2,4,8.5,NULL),
('SW2',2,2004,4,2,4,8.5,'blabasdasdala'),
('BAD BOYS',2,2006,4,2,4,8.5,'blasdasdabla');
