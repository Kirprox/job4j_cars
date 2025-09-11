package ru.job4j.cars.service;

import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Optional;

public interface BodyService {
    Body save(Body body);

    void update(Body body);

    void deleteById(Long id);

    Optional<Body> findById(Long id);

    List<Body> findAll();
}
