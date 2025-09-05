package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmOwnerRepository implements OwnerRepository {
    private final CrudRepository crudRepository;

    @Override
    public Owner save(Owner owner) {
        crudRepository.run(session -> session.persist(owner));
        return owner;
    }

    @Override
    public void update(Owner owner) {
        crudRepository.run(session -> session.merge(owner));
    }

    @Override
    public Optional<Owner> findById(int id) {
        return crudRepository.optional(
                "FROM Owner WHERE id = :fId", Owner.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Owner> findAll() {
        return crudRepository.query(
                "FROM Owner", Owner.class
        );
    }

    @Override
    public void deleteById(int id) {
        crudRepository.run(
                "DELETE FROM Owner WHERE id = :fId",
                Map.of("fId", id)
        );
    }
}
