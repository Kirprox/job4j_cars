package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.History;
import ru.job4j.cars.repository.HistoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleHistoryService implements HistoryService {
    private final HistoryRepository historyRepository;

    @Override
    public History save(History history) {
        return historyRepository.save(history);
    }

    @Override
    public void update(History history) {
        historyRepository.update(history);
    }

    @Override
    public void deleteById(int id) {
        historyRepository.deleteById(id);
    }

    @Override
    public Optional<History> findById(int id) {
        return historyRepository.findById(id);
    }

    @Override
    public List<History> findAll() {
        return historyRepository.findAll();
    }
}
