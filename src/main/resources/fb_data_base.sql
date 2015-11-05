CREATE DATABASE fb;
CREATE TABLE USER(
id_user VARCHAR(30) NOT NULL,
name VARCHAR(40) NOT NULL,
token VARCHAR(256) NOT NULL, PRIMARY KEY (id_user)
) ENGINE=InnoDB CHARSET=utf8;
CREATE TABLE post(
id INT AUTO_INCREMENT NOT NULL,
id_post VARCHAR (30) NOT NULL,
id_user VARCHAR(256) NOT NULL,
message TEXT NOT NULL, PRIMARY KEY (id)
) ENGINE=InnoDB CHARSET=utf8;
create table interest_of_posts(
id INT AUTO_INCREMENT NOT NULL,
post_id int not null,
interest int default null,
PRIMARY KEY(id),
foreign key (post_id) references post(id)  
)ENGINE=InnoDB CHARSET=utf8;