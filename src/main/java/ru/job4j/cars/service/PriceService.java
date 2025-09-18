package ru.job4j.cars.service;

import ru.job4j.cars.model.Price;

import java.util.List;
import java.util.Optional;

public interface PriceService {
    Price save(Price price);

    void update(Price price);

    void deleteById(Long id);

    Optional<Price> findById(Long id);

    List<Price> findAll();
}
