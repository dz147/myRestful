use postdb;
truncate table employee;

select * from author;
select * from post;
insert into post (aid, pname,content)values (3,'滴滴恐再难上线','因妹子出事太严重了');

ALTER TABLE post ADD writedate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

drop table employee;
show tables;
create table employee
(
  number varchar(20) PRIMARY KEY ,
  empName  varchar(20) ,
  empSex varchar(10),
  education varchar(10),
  monthly DECIMAL
)
select * from employee;
insert into employee values
  ('1001','为什么','男','本科',5000),
('1002','哒哒','女','博士',8000);
truncate table employee;

