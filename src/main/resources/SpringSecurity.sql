DROP DATABASE IF EXISTS `SpringSecurity`;  

CREATE DATABASE `SpringSecurity`;

USE `SpringSecurity`;

drop table if exists `users`;

create table users(
 id int auto_increment primary key,
 username varchar(50) unique not null,
 password varchar(50) not null,
 enabled boolean not null
)auto_increment=101;

drop table if exists authorities;

create table authorities (
	id int auto_increment primary key,
	username varchar(50) not null,
	authority varchar(50) not null
)auto_increment=101;

create unique index ix_auth_username on authorities (username,authority);


insert into users (username, password, enabled) values ("craig", "password", true);
insert into users (username, password, enabled) values ("john", "john123", true);

insert into authorities(username, authority) values("craig", "admin");
insert into authorities(username, authority) values("john", "read");

drop table if exists `customer`; 

CREATE TABLE `customer` (
  `id` int auto_increment primary key,
  `username` varchar(45) unique not null, 
  `email` varchar(45) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `role` varchar(45) NOT NULL
);

INSERT INTO `customer` (`username`, `email`, `pwd`, `role`) VALUES ('john', 'johndoe@example.com', '12345', 'admin');

select * from users;
select * from authorities;