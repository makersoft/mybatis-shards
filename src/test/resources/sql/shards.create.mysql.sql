CREATE TABLE maker_shards_user (
   id varchar(32) NOT NULL,
   username varchar(255) NOT NULL,
   password varchar(32) NOT NULL,
   gender int(1) NOT NULL,
   PRIMARY KEY (id)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE maker_shards_role(
	id varchar(32)  NOT NULL,
	name varchar(64) NOT NULL,
	code varchar(64) NOT null,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
