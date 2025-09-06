package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HbmEngineRepositoryTest {
    private static CrudRepository crudRepository;
    private static HbmEngineRepository repository;

    @BeforeAll
    static void init() {
        SessionFactory sf = new Configuration()
                .configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        repository = new HbmEngineRepository(crudRepository);
    }

    @AfterEach
    void cleanDb() {
        crudRepository.run("DELETE FROM Engine", Map.of());
    }

    @Test
    void whenAddNewEngineThenEngineRepHasSameEngine() {
        Engine engine = new Engine();
        engine.setName("v6");
        repository.create(engine);
        Engine result = repository.findById(engine.getId()).get();
        assertThat(result.getName()).isEqualTo(engine.getName());
    }

    @Test
    void whenAddNewEngineThenUpdateEngineThenEngineRepHasSameEngine() {
        Engine engine = new Engine();
        engine.setName("v6");
        repository.create(engine);
        engine.setName("v8");
        repository.update(engine);
        Engine result = repository.findById(engine.getId()).get();
        assertThat(result.getName()).isEqualTo(engine.getName());
    }

    @Test
    void whenAddNewEngineThenDeleteEngine() {
        Engine engine = new Engine();
        engine.setName("v6");
        repository.create(engine);
        repository.deleteById(engine.getId());
        Optional<Engine> result = repository.findById(engine.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void whenAddSomeEnginesThenEngineRepHasSameEngines() {
        Engine engine1 = new Engine();
        engine1.setName("v6");
        Engine engine2 = new Engine();
        engine2.setName("v8");
        repository.create(engine1);
        repository.create(engine2);
        List<Engine> result = repository.findAll();
        List<String> nameResult = result.stream()
                .map(Engine::getName)
                .collect(Collectors.toList());
        assertThat(nameResult).containsExactlyInAnyOrder("v6", "v8");
    }
}