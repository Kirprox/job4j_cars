package ru.job4j.cars.repository;

import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

public interface PriceHistoryRepository {
    PriceHistory create(PriceHistory priceHistory);

    void update(PriceHistory priceHistory);

    void deleteById(int id);

    Optional<PriceHistory> findById(int id);

    List<PriceHistory> findAll();
}
