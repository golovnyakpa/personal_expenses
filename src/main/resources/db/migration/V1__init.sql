create table expenses
(
    id          bigserial primary key,
    title       varchar(300),
    username    varchar(50) not null,
    amount      int,
    category    varchar(300),
    description varchar(1000),
    date_time   timestamp
);

create table users
(
    id       bigserial primary key,
    username varchar(50)  not null unique,
    password varchar(100) not null,
    email    varchar(100) not null
);

create table roles
(
    id   serial primary key,
    name varchar(50) not null
);

CREATE TABLE users_roles
(
    user_id bigint not null,
    role_id int    not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

insert into roles (name)
values ('USER');
