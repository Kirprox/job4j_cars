package ru.job4j.cars.service;

import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    Owner save(Owner owner);

    void update(Owner owner);

    void deleteById(Long id);

    Optional<Owner> findById(Long id);

    List<Owner> findAll();
}
