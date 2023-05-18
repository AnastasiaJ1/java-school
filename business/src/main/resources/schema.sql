create table "Employees"
(
    id uuid not null
        primary key,
    lastname varchar(20) not null,
    firstname varchar(20) not null,
    surname varchar(20),
    jobtitle varchar(20),
    account varchar(40)
        unique,
    email varchar(20),
    status varchar(20) not null
);


create table "Project"
(
    id uuid not null
        primary key,
    code varchar(40) not null
        unique,
    name varchar(20) not null,
    description varchar(300),
    status varchar(20) not null
);


create table "ProjectParticipants"
(
    id uuid not null
        primary key,
    employeeid uuid not null
        references "Employees"(id),
    projectid  uuid not null
        references "Project"(id),
    role varchar(40)
);


create table "Tasks"
(
    id uuid not null
        primary key,
    name varchar(20) not null,
    description varchar(300),
    executor uuid
        references "Employees"(id),
    laborcostshours integer     not null,
    deadline timestamp   not null,
    status varchar(20),
    author uuid
        references "Employees"(id),
    creationdate timestamp,
    changedate timestamp
);