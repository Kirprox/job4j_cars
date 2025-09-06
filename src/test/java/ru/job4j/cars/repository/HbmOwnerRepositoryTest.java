package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HbmOwnerRepositoryTest {
    private static CrudRepository crudRepository;
    private static OwnerRepository repository;

    @BeforeAll
    static void init() {
        SessionFactory sf = new Configuration()
                .configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        repository = new HbmOwnerRepository(crudRepository);
    }

    @AfterEach
    void cleanDb() {
        crudRepository.run("DELETE FROM Owner", Map.of()
        );
    }

    @Test
    void whenAddNewOwnerThenOwnerRepHasSameOwner() {
        Owner owner = new Owner();
        owner.setName("Kir");
        repository.save(owner);
        Owner result = repository.findById(owner.getId()).get();
        assertThat(result.getName()).isEqualTo(owner.getName());
    }

    @Test
    void whenAddNewOwnerThenUpdateOwnerThenOwnerRepHasSameOwner() {
        Owner owner = new Owner();
        owner.setName("Kir");
        repository.save(owner);
        owner.setName("Andrew");
        repository.update(owner);
        Owner result = repository.findById(owner.getId()).get();
        assertThat(result.getName()).isEqualTo(owner.getName());
    }

    @Test
    void whenAddSomeOwnersThenOwnerRepHasSameOwners() {
        Owner owner = new Owner();
        owner.setName("Kir");
        Owner owner1 = new Owner();
        owner1.setName("Andrew");
        repository.save(owner);
        repository.save(owner1);
        List<Owner> oners = repository.findAll();
        List<String> names = oners.stream()
                .map(Owner::getName)
                .collect(Collectors.toList());
        assertThat(names).containsExactlyInAnyOrder("Kir", "Andrew");
    }

    @Test
    void whenAddNewOwnerThenDeleteOwner() {
        Owner owner = new Owner();
        owner.setName("Kir");
        repository.save(owner);
        repository.deleteById(owner.getId());
        Optional<Owner> result = repository.findById(owner.getId());
        assertThat(result).isEmpty();
    }
}