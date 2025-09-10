package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePriceHistoryService implements PriceHistoryService {
    private final PriceHistoryRepository priceHistoryRepository;

    @Override
    public PriceHistory create(PriceHistory priceHistory) {
        return priceHistoryRepository.create(priceHistory);
    }

    @Override
    public void update(PriceHistory priceHistory) {
        priceHistoryRepository.update(priceHistory);
    }

    @Override
    public void deleteById(Long id) {
        priceHistoryRepository.deleteById(id);
    }

    @Override
    public Optional<PriceHistory> findById(Long id) {
        return priceHistoryRepository.findById(id);
    }

    @Override
    public List<PriceHistory> findAll() {
        return priceHistoryRepository.findAll();
    }
}
