create table owners(
    id serial primary key,
    name VARCHAR(40) NOT NULL,
    user_id INT NOT NULL REFERENCES auto_user(id)
);