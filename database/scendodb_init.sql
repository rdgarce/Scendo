CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE OR REPLACE FUNCTION uuid_or_null(str text)
RETURNS uuid AS $$
BEGIN
  RETURN str::uuid;
EXCEPTION WHEN invalid_text_representation THEN
  RETURN NULL;
END;
$$ LANGUAGE plpgsql;

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
	scendoTime timestamp,

	PRIMARY KEY (scendoId)

);

CREATE TABLE Friendships (

	userId1 uuid,
	userId2 uuid,
	friendsSince timestamp,


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
