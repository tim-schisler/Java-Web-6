/***********************************************************
*  Create the database named hw6, its tables, and a database user
************************************************************/
DROP DATABASE IF EXISTS hw6;
CREATE DATABASE hw6;
USE hw6;
CREATE TABLE User (
UserID INT NOT NULL AUTO_INCREMENT,
Email VARCHAR(50),
FirstName VARCHAR(50),
LastName VARCHAR(50),
Password VARCHAR(50),
PRIMARY KEY(UserID)
);
INSERT INTO User
(Email, FirstName, LastName, Password)
VALUES
('bat@gmail.com', 'Bruce', 'Wayne', 'bat'),
('spider@gmail.com', 'Peter', 'Parker', 'spider'),
('wonder@gmail.com', 'Gal', 'Gadot', 'wonder');
CREATE TABLE Book (
BookID INT NOT NULL AUTO_INCREMENT,
Cover VARCHAR(120),
Title VARCHAR(50),
Price FLOAT,
PRIMARY KEY(BookID)
);

-- Insert Book entries here
INSERT INTO Book (Cover, Title, Price)
VALUES
('https://images-na.ssl-images-amazon.com/images/I/51Ky9l4DYEL.jpg', 'The Man in the High Castle', 11.29),
('https://images-na.ssl-images-amazon.com/images/I/51vTzBUyG9L._SX302_BO1,204,203,200_.jpg', 'The Caves of Steel', 4.92),
('https://images.penguinrandomhouse.com/cover/9780553212525', '20000 Leagues Under the Sea', 3.89),
('https://www.parigibooks.com/pictures/medium/11769.jpg?v%3D1454036881', 'The Time Machine', 7.99);

-- Create student and grant privileges
DELIMITER //
CREATE PROCEDURE drop_user_if_exists()
BEGIN
DECLARE userCount BIGINT DEFAULT 0 ;
SELECT COUNT(*) INTO userCount FROM mysql.user
WHERE User = 'student' and  Host = 'localhost';
IF userCount > 0 THEN
DROP USER student@localhost;
END IF;
END ; //
DELIMITER ;


CALL drop_user_if_exists() ;
CREATE USER student@localhost IDENTIFIED BY 'sesame';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP
ON hw6.*
TO student@localhost;
USE hw6;