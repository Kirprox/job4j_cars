package ru.job4j.cars.service;

import ru.job4j.cars.model.History;

import java.util.List;
import java.util.Optional;

public interface HistoryService {
    History save(History history);

    void update(History history);

    void deleteById(Long id);

    Optional<History> findById(Long id);

    List<History> findAll();
}
