package ru.job4j.cars.service;

import ru.job4j.cars.dto.CarDto;
import ru.job4j.cars.model.Car;

import java.util.List;

public interface CarService {
    Car create(Car car);

    void update(Car car);

    void deleteById(Long id);

    CarDto findById(Long id);

    List<CarDto> findAll();
}
