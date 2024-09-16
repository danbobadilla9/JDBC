drop table if exists person;

CREATE TABLE person(
id int auto_increment primary key,
name varchar(100) not null,
last_name varchar(100) not null,
nickname varchar(300)
);