create table expenses
(
    id          bigserial primary key,
    title       varchar(300),
    amount      int,
    category    varchar(300),
    description varchar(1000),
    date_time   timestamp
);

-- create table expenses
-- (
--     id     INTEGER PRIMARY KEY,
--     title  TEXT,
--     amount INTEGER
-- );
