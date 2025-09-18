package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.CarDto;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimpleCarService implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car create(Car car) {
        return carRepository.create(car);
    }

    @Override
    public void update(Car car) {
        carRepository.update(car);
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public CarDto findById(Long id) {
        return carRepository.findById(id)
                .map(CarDto::fromEntity).get();
    }

    @Override
    public List<CarDto> findAll() {
        return carRepository.findAll().stream()
                .map(CarDto::fromEntity).collect(Collectors.toList());
    }
}
