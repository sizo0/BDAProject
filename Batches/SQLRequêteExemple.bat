use y;
DROP TABLE Persons;
CREATE TABLE Persons
(
PersonID int,
LastName varchar(255),
FirstName varchar(255),
Address varchar(255),
City varchar(255)
);

INSERT INTO Persons VALUES (1,'name','prenom','adresse','ville');
INSERT INTO Persons VALUES (2,'name2','prenom2','adresse2','ville2');
INSERT INTO Persons VALUES (3,'name3','prenom3','adresse3','ville3');
INSERT INTO Persons VALUES (4,'name4','prenom4','adresse4','ville4');


select * from Persons;