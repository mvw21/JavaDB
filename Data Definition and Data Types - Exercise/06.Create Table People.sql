CREATE TABLE Users(
id INT(11) PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(30) NOT NULL,
password VARCHAR(26) NOT NULL,
profile_picture BLOB,
last_login_time DATETIME,
is_deleted BOOLEAN
);

INSERT INTO Users (id,username,password,profile_picture,last_login_time,is_deleted)
VALUES
(1,'Pesho','asdf',NULL,'0000-00-00 00:00:00',False),
(2,'Pesho','asdf',NULL,'1922-02-00 02:30:00',False),
(3,'Pesho','asdf',NULL,'0003-12-20 00:00:00',True),
(4,'Pesho','asdf',NULL,'0400-10-03 00:00:00',False),
(5,'Pesho','asdf',NULL,'1000-11-01 00:00:00',True);
