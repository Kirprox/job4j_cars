CREATE TABLE auto_post
(
    id          SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    created     TIMESTAMP NOT NULL,
    auto_user_id INT NOT NULL REFERENCES auto_user(id),
    car_id INT REFERENCES car(id),
    file_id int NOT NULL REFERENCES files(id)
);