CREATE TABLE history_owner(
    id SERIAL PRIMARY KEY,
    owner_id INT NOT NULL REFERENCES owners(id),
    car_id INT NOT NULL REFERENCES car(id),
    UNIQUE (car_id, owner_id)
);