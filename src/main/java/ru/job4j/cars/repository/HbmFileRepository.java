package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmFileRepository implements FileRepository {
    private final CrudRepository crudRepository;

    @Override
    public File create(File file) {
        crudRepository.run(session -> session.persist(file));
        return file;
    }

    @Override
    public void update(File file) {
        crudRepository.run(session -> session.merge(file));
    }

    @Override
    public void deleteById(int id) {
        crudRepository.run(
                "DELETE FROM File WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public Optional<File> findById(int id) {
        return crudRepository.optional(
                "FROM File WHERE id = :fId", File.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<File> findAll() {
        return crudRepository.query(
                "FROM File", File.class
        );
    }
}
