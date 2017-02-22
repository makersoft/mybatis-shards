CREATE TABLE maker_shards_user (
   id varchar(32) NOT NULL,
   username varchar(255) NOT NULL,
   password varchar(32) NOT NULL,
   age int NOT NULL,
   gender int NOT NULL,
   PRIMARY KEY (id)
 );
 
CREATE TABLE maker_shards_role(
	id varchar(32)  NOT NULL,
	name varchar(64) NOT NULL,
	code varchar(64) NOT null,
	PRIMARY KEY (id)
) ;
