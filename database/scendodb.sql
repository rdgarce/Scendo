CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Users (

	userId uuid,
	name varchar(255),
	email varchar(255),
	password varchar(255),

	PRIMARY KEY (userId)

);

CREATE TABLE Scendos (

	scendoId uuid,
	location varchar(255),
	scendoTime date,

	PRIMARY KEY (scendoId)

);

CREATE TABLE Friendships (

	userId1 uuid,
	userId2 uuid,
	friendsSince time,


	PRIMARY KEY (userId1,userId2),

	FOREIGN KEY (userId1) REFERENCES Users(userId),
	FOREIGN KEY (userId2) REFERENCES Users(userId)

);

CREATE TABLE User_Scendo (

	userId uuid,
	scendoId uuid,
	isCreator boolean,

	PRIMARY KEY (userId,scendoId),

	FOREIGN KEY (userId) REFERENCES Users(userId),
	FOREIGN KEY (scendoId) REFERENCES Scendos(scendoId)

);
