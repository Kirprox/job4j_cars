CREATE TABLE owners (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    user_id INT NOT NULL REFERENCES auto_user(id)
);