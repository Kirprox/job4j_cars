package ru.job4j.cars.repository;

import ru.job4j.cars.model.History;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository {
    History save(History history);

    void update(History history);

    void deleteById(int id);

    Optional<History> findById(int id);

    List<History> findAll();
}
