package ru.job4j.cars.repository;

import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Optional;

public interface BodyRepository {
    Body create(Body body);

    void update(Body body);

    void deleteById(Long id);

    Optional<Body> findById(Long id);

    List<Body> findAll();
}
