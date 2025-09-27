package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Price;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbmPriceRepositoryTest {
    private static CrudRepository crudRepository;
    private static HbmPriceRepository repository;

    @BeforeAll
    static void init() {
        SessionFactory sf = new Configuration()
                .configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        repository = new HbmPriceRepository(crudRepository);
    }

    @AfterEach
    void cleanDb() {
        crudRepository.run("DELETE FROM Price", Map.of());
    }

    @Test
    void whenAddNewPriceThenPriceRepHasSamePrice() {
        Price price = new Price();
        price.setPrice(100L);
        repository.create(price);
        Price result = repository.findById(price.getId()).get();
        assertThat(result.getPrice()).isEqualTo(price.getPrice());
    }

    @Test
    void whenAddNewPriceThenUpdatePriceThenPriceRepHasSamePrice() {
        Price price = new Price();
        price.setPrice(100L);
        repository.create(price);
        price.setPrice(200L);
        repository.update(price);
        Price result = repository.findById(price.getId()).get();
        assertThat(result.getPrice()).isEqualTo(price.getPrice());
    }

    @Test
    void whenAddNewPriceThenDeletePrice() {
        Price price = new Price();
        price.setPrice(100L);
        repository.create(price);
        repository.deleteById(price.getId());
        Optional<Price> result = repository.findById(price.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void whenAddSomePricesThenPriceRepHasSamePrices() {
        Price price = new Price();
        price.setPrice(100L);
        Price price2 = new Price();
        price2.setPrice(1001L);
        repository.create(price);
        repository.create(price2);
        List<Price> prices = repository.findAll();
        List<Long> results = prices.stream()
                .map(Price::getPrice)
                .toList();
        assertThat(results).containsExactlyInAnyOrder(100L, 1001L);
    }
}