package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Car create(Car car);

    void update(Car car);

    void deleteById(Long id);

    Optional<Car> findById(Long id);

    List<Car> findAll();
}
