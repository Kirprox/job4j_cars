package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.History;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmHistoryRepository implements HistoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public History save(History history) {
        crudRepository.run(session -> session.persist(history));
        return history;
    }

    @Override
    public void update(History history) {
        crudRepository.run(session -> session.merge(history));
    }

    @Override
    public void deleteById(int id) {
        crudRepository.run(
                "FROM History WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public Optional<History> findById(int id) {
        return crudRepository.optional(
                "FROM History WHERE id = :fId", History.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<History> findAll() {
        return crudRepository.query(
                "FROM History", History.class
        );
    }
}
