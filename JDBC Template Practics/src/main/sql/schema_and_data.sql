create table course(
                       id serial primary key,
                       name varchar(30) unique not null,
                       begin_date char(10) not null,
                       end_date char(10) not null,
                       teacher_id integer not null
);

insert into course(name, begin_date, end_date, teacher_id) VALUES ('Программирование','01.09.2021','01.06.2022',1);
insert into course(name, begin_date, end_date, teacher_id) VALUES ('Английский язык','02.09.2021','01.06.2022',2);
insert into course(name, begin_date, end_date, teacher_id) VALUES ('a','a','a',2);

create table subject(
                        id serial primary key,
                        name varchar(30) not null,
                        time varchar(20) not null,
                        course_id integer,
                        foreign key (course_id) references course(id)
);
insert into subject(name, time, course_id) values ('Java','ПН 13:00',1);

create table teacher(
                        id serial primary key,
                        first_name varchar(20) not null,
                        last_name varchar(20) not null,
                        seniority integer check (seniority>=0)
                    );
insert into teacher(first_name, last_name, seniority) VALUES ('Иванов','Иван',10);
insert into teacher(first_name, last_name, seniority) VALUES ('Борисов','Борис',5);

create table teacher_of_course(
                                  teacher_id integer,
                                  course_id integer,
                                  foreign key (teacher_id) references teacher(id),
                                  foreign key (course_id) references course(id)
);
insert into teacher_of_course(teacher_id, course_id) VALUES (1,1);
insert into teacher_of_course(teacher_id, course_id) VALUES (2,2);

create table student(
                        id serial primary key,
                        first_name varchar(20) not null,
                        last_name varchar(20) not null,
                        group_number integer check(group_number>0)
);
insert into student(first_name, last_name, group_number) VALUES ('Кристина','Выдренкова',1);
insert into student(first_name, last_name, group_number) VALUES ('Диана','Гильфанова',1);
insert into student(first_name, last_name, group_number) values ('Петр','Петров',2);

create table students_of_course(
                                   course_id integer,
                                   student_id integer,
                                   foreign key (course_id) references course(id),
                                   foreign key (student_id) references student(id)
);
insert into students_of_course(course_id, student_id) VALUES (1,1);
insert into students_of_course(course_id, student_id) VALUES (1,2);
insert into students_of_course(course_id, student_id) VALUES (2,1);
insert into students_of_course(course_id, student_id) VALUES (2,3);

drop table teacher;
drop table student;
drop table subject;
drop table course;
drop table students_of_course;