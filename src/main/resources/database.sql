-- noinspection SqlNoDataSourceInspectionForFile

-- noinspection SqlDialectInspectionForFile

#--------------------MOVIE_WORLD-----------------------/
drop database movie_world;
create database if not exists movie_world;
use movie_world;



#----------------------USER-----------------------/
drop table if exists user;
create table user(
	id int primary key auto_increment,
    name varchar(30) not null,
    sex varchar(10) not null,
    date_of_birth date not null,
    mobile long default null,
    email varchar(255) not null unique key,
    password char(68) not null,
    active boolean default true
)engine=InnoDB auto_increment=1 default charset=latin1;
insert into user values
(1,'One','MALE','2010-03-20',13243532,'one@gmail.com','{bcrypt}$2a$10$yjuCKquMIwAFW8hAXgRiB.fxbqN1aHQp6LRZh2WDz83Bb.Mml141W',true),
(2,'Two','FEMALE','20120220',13243532,'two@gmail.com','{bcrypt}$2a$10$yjuCKquMIwAFW8hAXgRiB.fxbqN1aHQp6LRZh2WDz83Bb.Mml141W',true),
(3,'Three','MALE','20110314',13243532,'three@gmail.com','{bcrypt}$2a$10$yjuCKquMIwAFW8hAXgRiB.fxbqN1aHQp6LRZh2WDz83Bb.Mml141W',true);



#----------------------PERMISSION---------------------/
drop table if exists permission;
create table permission(
    id int primary key auto_increment,
    name varchar(20) not null unique
)engine=InnoDB auto_increment=1 default charset=latin1;
insert into permission values
(1,'CREATE'),(2,'READ'),(3,'UPDATE'),(4,'DELETE'),(5,'READ_ALL');



#-----------------------ROLE--------------------/
drop table if exists role;
create table role(
    id int primary key auto_increment,
    name varchar(30) not null unique
)engine=InnoDB auto_increment=1 default charset=latin1;
insert into role values
(1,'ADMINISTRATOR'),(2,'EMPLOYEE'),(3,'CUSTOMER');



#---------------ASSIGN_PERMISSION_TO_ROLE---------------/
drop table if exists assign_permission_to_role;
create table assign_permission_to_role(
    role_id int not null,
    permission_id int not null,
    constraint fk_perm_role1 foreign key (role_id) references role(id),
    constraint fk_perm_role2 foreign key (permission_id) references permission(id)
);
insert into assign_permission_to_role values
(1,1),(1,2),(1,3),(1,4),(1,5),(2,2),(2,5),(3,2);



#--------------------ASSIGN_ROLE_TO_USER--------------/
drop table if exists assign_role_to_user;
create table assign_role_to_user(
    user_id int not null,
    role_id int not null,
    constraint fk_role_user1 foreign key (user_id) references user(id),
    constraint fk_role_user2 foreign key (role_id) references role(id)
);
insert into assign_role_to_user values
(1,1),(1,2),(2,2),(3,3);



#---------------AUDITORIUM---------------/
drop table if exists auditorium;
create table auditorium(
	id int primary key auto_increment,
	name varchar(20) not null,
	gold_capacity int not null,
	platinum_capacity int not null
)engine=InnoDB auto_increment=1 default charset=latin1;
insert into auditorium values
(1,'Audi 1',50,30),
(2,'Audi 2',40,30),
(3,'Audi 3',80,40);



#----------------MOVIE------------------/
drop table if exists movie;
create table movie(
	id int primary key auto_increment,
	name varchar(30) not null,
	description varchar(100) not null,
	cast varchar(500) not null,
	language varchar(100) not null,
	genre varchar(100) not null,
	certification varchar(30) not null,
	runtime int not null
)engine=InnoDB auto_increment=1 default charset=latin1;
insert into movie values
(1,'Movie 1','Its a fantastic movie','Amana,Pamna,Tamna','Hindi,English','Thriller,Comedy','PG-13',135),
(2,'Movie 2','Its an awesome movie','Seeta,Geeta,Peeta','Hindi,English,Farsi','Romance,Comedy','U',150),
(3,'Movie 3','Its a new movie','Chaman,Dhaman,Kaman','Hindi,English','Thriller,Action','A',116);



#----------------PRICE-----------------/
drop table if exists price;
create table price(
	id int primary key auto_increment,
	gold_price double not null,
	platinum_price double not null
)engine=InnoDB auto_increment=1 default charset=latin1;
insert into price values
(1,300,400),
(2,200,300),
(3,500,700);



#-------------------SHOW-------------------/
drop table if exists shows;
create table shows(
	id int primary key auto_increment,
	movie_id int not null,
	auditorium_id int not null,
	type varchar(3) not null,
	gold_seats int not null,
	platinum_seats int not null,
	date_time datetime not null,
	price_id int not null,
	constraint fk_movie foreign key (movie_id) references movie(id),
	constraint fk_auditorium foreign key (auditorium_id) references auditorium(id),
	constraint fk_price foreign key (price_id) references price(id)
)engine=InnoDB auto_increment=1 default charset=latin1;
insert into shows values
(1,1,2,'2D',40,30,'2020-10-23 05:00',2),
(2,1,3,'3D',80,40,'2020-10-25 03:30',3),
(3,2,3,'3D',80,40,'2020-10-09 01:00',2);



#----------------TICKET-----------------/
drop table if exists ticket;
create table ticket(
	id int primary key auto_increment,
	user_id int not null,
	show_id int not null,
	booking_date datetime not null,
	seat_number varchar(4) not null,
	seat_type varchar(15) not null,
	price double not null,
	constraint fk_user foreign key (user_id) references user(id),
	constraint fk_show foreign key (show_id) references shows(id)
)engine=InnoDB auto_increment=1 default charset=latin1;
insert into ticket values
(1,2,1,'2020-10-23 05:00','B12','GOLD',200),
(2,1,2,'2020-10-25 03:30','M13','PLATINUM',700),
(3,3,2,'2020-10-25 03:30','N22','PLATINUM',700),
(4,2,3,'2020-10-09 01:00','B11','GOLD',200);



