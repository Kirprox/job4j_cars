package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Price;
import ru.job4j.cars.repository.PriceRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePriceService implements PriceService {
    PriceRepository priceRepository;

    @Override
    public Price save(Price price) {
        return priceRepository.create(price);
    }

    @Override
    public void update(Price price) {
        priceRepository.update(price);
    }

    @Override
    public void deleteById(Long id) {
        priceRepository.deleteById(id);
    }

    @Override
    public Optional<Price> findById(Long id) {
        return priceRepository.findById(id);
    }

    @Override
    public List<Price> findAll() {
        return priceRepository.findAll();
    }
}
