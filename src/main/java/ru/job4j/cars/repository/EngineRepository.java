package ru.job4j.cars.repository;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

public interface EngineRepository {
    Engine create(Engine engine);

    void update(Engine engine);

    void deleteById(Long id);

    Optional<Engine> findById(Long id);

    List<Engine> findAll();
}
