# docker run --name test-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123 -d mysql
create database test;
use test;
create table employees (
    id int not null auto_increment primary key ,
    name varchar(100) not null ,
    department varchar(100) not null,
    working boolean default true
);
show tables;
insert into employees (name, department) values ('Tom', 'Emergency');
insert into employees (name, department) values ('Luke', 'Emergency');
select * from employees;
