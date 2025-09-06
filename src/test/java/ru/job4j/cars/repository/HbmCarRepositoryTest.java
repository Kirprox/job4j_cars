package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HbmCarRepositoryTest {
    private static CrudRepository crudRepository;
    private static HbmCarRepository repository;

    @BeforeAll
    static void init() {
        SessionFactory sf = new Configuration()
                .configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        repository = new HbmCarRepository(crudRepository);
    }

    @AfterEach
    void cleanDB() {
        crudRepository.run("DELETE FROM Car", Map.of());
        crudRepository.run("DELETE FROM Owner", Map.of());
    }

    @Test
    void whenAddNewCarThenCarRepHasSameCar() {
        Car car1 = new Car();
        car1.setName("toyota");
        repository.create(car1);
        Car result = repository.findById(car1.getId()).get();
        assertThat(result.getName()).isEqualTo(car1.getName());
    }

    @Test
    void whenAddNewCarThenUpdateCarThenCarRepHasSameCar() {
        Car car1 = new Car();
        car1.setName("toyota");
        repository.create(car1);
        car1.setName("kamaz");
        repository.update(car1);
        Car result = repository.findById(car1.getId()).get();
        assertThat(result.getName()).isEqualTo(car1.getName());
    }

    @Test
    void whenAddNewCarThenDeleteCar() {
        Car car1 = new Car();
        car1.setName("toyota");
        repository.create(car1);
        repository.deleteById(car1.getId());
        Optional<Car> result = repository.findById(car1.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void whenAddSomeCarsThenCarRepHasSameCars() {
        Car car1 = new Car();
        car1.setName("toyota");
        Car car2 = new Car();
        car2.setName("kamaz");
        repository.create(car1);
        repository.create(car2);

        List<Car> result = repository.findAll();

        List<String> brands = result.stream()
                .map(Car::getName)
                .collect(Collectors.toList());
        assertThat(brands)
                .containsExactlyInAnyOrder("toyota", "kamaz");
    }
}