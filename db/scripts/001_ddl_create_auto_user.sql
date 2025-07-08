CREATE TABLE auto_user
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR not null,
    password VARCHAR not null
);