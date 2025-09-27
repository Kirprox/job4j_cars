package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HbmBodyRepositoryTest {
    private static CrudRepository crudRepository;
    private static HbmBodyRepository repository;

    @BeforeAll
    static void init() {
        SessionFactory sf = new Configuration()
                .configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        repository = new HbmBodyRepository(crudRepository);
    }

    @AfterEach
    void cleanDb() {
        crudRepository.run("DELETE FROM Body", Map.of());
    }

    @Test
    void whenAddNewBodyThenBodyRepHasSameBody() {
        Body body = new Body();
        body.setName("coupe");
        repository.create(body);
        Body result = repository.findById(body.getId()).get();
        assertThat(result.getName()).isEqualTo(body.getName());
    }

    @Test
    void whenAddNewBodyThenUpdateBodyThenBodyRepHasSameBody() {
        Body body = new Body();
        body.setName("coupe");
        repository.create(body);
        body.setName("hatchback");
        repository.update(body);
        Body result = repository.findById(body.getId()).get();
        assertThat(result.getName()).isEqualTo(body.getName());
    }

    @Test
    void whenAddNewBodyThenDeleteBody() {
        Body body = new Body();
        body.setName("coupe");
        repository.create(body);
        repository.deleteById(body.getId());
        Optional<Body> result = repository.findById(body.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void whenAddSomeBodiesThenBodyRepHasSameBodies() {
        Body body = new Body();
        Body body2 = new Body();
        body.setName("coupe");
        body2.setName("hatchback");
        repository.create(body);
        repository.create(body2);
        List<Body> bodyList = repository.findAll();
        List<String> names = bodyList.stream()
                .map(Body::getName)
                .collect(Collectors.toList());
        assertThat(names).containsExactlyInAnyOrder("coupe", "hatchback");
    }
}