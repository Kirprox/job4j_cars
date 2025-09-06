package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class HbmHistoryRepositoryTest {
    private static CrudRepository crudRepository;
    private static HbmHistoryRepository repository;

    @BeforeAll
    static void init() {
        SessionFactory sf = new Configuration()
                .configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        repository = new HbmHistoryRepository(crudRepository);
    }

    @AfterEach
    void cleanDb() {
        crudRepository.run("DELETE FROM History", Map.of());
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }
}