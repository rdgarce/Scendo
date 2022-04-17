CREATE TABLE User(

	user_id varchar(255),
	name varchar(255),
	email varchar(255),
	pass_word varchar(255),

	primary key(user_id)

);


CREATE TABLE Frindship(

	user_id1 varchar(255),
	user_id2 varchar(255),
	frind_Since time,


	primary key(user_id1,user_id2),

	foreign key(user_id1) references User(user_id1),
	foreign key(user_id2) references User(user_id2)

);


CREATE TABLE User_Scendo(

	user_id varchar(255),
	scendo_id varchar(255),
	is_creator boolean,

	primary key(user_id,scendo_id),

	foreign key(user_id) references User(user_id),
	foreign key(scendo_id) references Scendo(scendo_id)

);


CREATE TABLE Scendo(

	scendo_id,
	location varchar(255),
	scendo_time date,

	primary key(scendo_id)

);