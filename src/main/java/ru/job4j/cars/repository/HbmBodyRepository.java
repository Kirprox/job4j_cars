package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmBodyRepository implements BodyRepository {
    CrudRepository crudRepository;

    @Override
    public Body create(Body body) {
        crudRepository.run(session -> session.persist(body));
        return body;
    }

    @Override
    public void update(Body body) {
        crudRepository.run(session -> session.merge(body));
    }

    @Override
    public void deleteById(Long id) {
        crudRepository.query(
                "DELETE FROM Body WHERE id = :fId", Body.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Optional<Body> findById(Long id) {
        return crudRepository.optional(
                "FROM Body WHERE id = :fId", Body.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Body> findAll() {
        return crudRepository.query(
                "FROM Body", Body.class
        );
    }
}
