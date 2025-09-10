package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPriceHistoryRepository implements PriceHistoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public PriceHistory create(PriceHistory priceHistory) {
        crudRepository.run(session -> session.persist(priceHistory));
        return priceHistory;
    }

    @Override
    public void update(PriceHistory priceHistory) {
        crudRepository.run(session -> session.merge(priceHistory));
    }

    @Override
    public void deleteById(Long id) {
        crudRepository.run(
                "DELETE FROM PriceHistory WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public Optional<PriceHistory> findById(Long id) {
        return crudRepository.optional(
                "FROM PriceHistory WHERE id = :fId", PriceHistory.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<PriceHistory> findAll() {
        return crudRepository.query(
                "FROM PriceHistory", PriceHistory.class
        );
    }
}
