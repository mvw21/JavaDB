CREATE TABLE `minions` (
`id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(255) NOT NULL,
`age` INT(11) DEFAULT NULL
);

CREATE TABLE `towns`(
`id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(255) DEFAULT NULL
);
