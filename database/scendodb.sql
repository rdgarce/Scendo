CREATE TABLE Users (

	userId varchar(255),
	name varchar(255),
	email varchar(255),
	password varchar(255),

	PRIMARY KEY (userId)

);

CREATE TABLE Scendos (

	scendoId varchar(255),
	location varchar(255),
	scendoTime date,

	PRIMARY KEY (scendoId)

);

CREATE TABLE Friendships (

	userId1 varchar(255),
	userId2 varchar(255),
	frindsSince time,


	PRIMARY KEY (userId1,userId2),

	FOREIGN KEY (userId1) REFERENCES Users(userId),
	FOREIGN KEY (userId2) REFERENCES Users(userId)

);


CREATE TABLE User_Scendo (

	userId varchar(255),
	scendoId varchar(255),
	isCreator boolean,

	PRIMARY KEY (userId,scendoId),

	FOREIGN KEY (userId) REFERENCES Users(userId),
	FOREIGN KEY (scendoId) REFERENCES Scendos(scendoId)

);
