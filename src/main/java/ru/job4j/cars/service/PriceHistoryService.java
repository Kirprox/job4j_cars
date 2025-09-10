package ru.job4j.cars.service;

import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

public interface PriceHistoryService {
    PriceHistory create(PriceHistory priceHistory);

    void update(PriceHistory priceHistory);

    void deleteById(Long id);

    Optional<PriceHistory> findById(Long id);

    List<PriceHistory> findAll();
}
