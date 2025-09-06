package ru.job4j.cars.repository;

import ru.job4j.cars.model.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository {
    File create(File file);

    void update(File file);

    void deleteById(int id);

    Optional<File> findById(int id);

    List<File> findAll();
}
