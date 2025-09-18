package ru.job4j.cars.repository;

import ru.job4j.cars.model.Price;

import java.util.List;
import java.util.Optional;

public interface PriceRepository {
    Price create(Price price);

    void update(Price price);

    void deleteById(Long id);

    Optional<Price> findById(Long id);

    List<Price> findAll();
}
