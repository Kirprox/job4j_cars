CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    engine_id INT NOT NULL REFERENCES engine(id),
    body_id INT NOT NULL REFERENCES body(id),
    price_id BIGINT NOT NULL UNIQUE REFERENCES price(id)
);