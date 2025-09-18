package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Price;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPriceRepository implements PriceRepository {
    CrudRepository crudRepository;

    @Override
    public Price create(Price price) {
        crudRepository.run(session -> session.persist(price));
        return price;
    }

    @Override
    public void update(Price price) {
        crudRepository.run(session -> session.merge(price));
    }

    @Override
    public void deleteById(Long id) {
        crudRepository.query(
                "DELETE FROM Price WHERE id = :fId", Price.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Optional<Price> findById(Long id) {
        return crudRepository.optional(
                "FROM Price WHERE id = :fId", Price.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Price> findAll() {
        return crudRepository.query(
                "FROM Price", Price.class
        );
    }
}
