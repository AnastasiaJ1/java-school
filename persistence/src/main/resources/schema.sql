create table employees
(
    id uuid not null
        primary key,
    lastname varchar(40) not null,
    firstname varchar(40) not null,
    surname varchar(40),
    job_title varchar(40),
    account varchar(40)
        unique,
    email varchar(40),
    status varchar(40) not null
);


create table projects
(
    id uuid not null
        primary key,
    code varchar(40) not null
        unique,
    name varchar(40) not null,
    description varchar(200),
    status varchar(40) not null
);


create table project_participants
(

    employee_id uuid not null
        references employees(id),
    project_id  uuid not null
        references projects(id),
    role varchar(40),
    constraint primary key (employee_id, project_id)
);


create table tasks
(
    id uuid not null
        primary key,
    name varchar(40) not null,
    description varchar(200),
    executor uuid
        references employees(id),
    labor_costs_hours integer     not null,
    deadline timestamp   not null,
    status varchar(40),
    author uuid
        references employees(id),
    creation_date timestamp,
    change_date timestamp,
    project_id uuid
        references projects(id)
);