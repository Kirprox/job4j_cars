package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.PriceHistory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HbmPriceHistoryRepositoryTest {
    private static CrudRepository crudRepository;
    private static HbmPriceHistoryRepository repository;

    @BeforeAll
    static void init() {
        SessionFactory sf = new Configuration()
                .configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        repository = new HbmPriceHistoryRepository(crudRepository);
    }

    @AfterEach
    void cleanDb() {
        crudRepository.run("DELETE FROM PriceHistory", Map.of());
    }

    @Test
    void whenAddNewPriceHistoryThenPriceHistoryRepHasSamePriceHistory() {
        PriceHistory pHistory = new PriceHistory();
        pHistory.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        repository.create(pHistory);
        PriceHistory result = repository.findById(pHistory.getId()).get();
        assertThat(result.getCreated()).isEqualTo(pHistory.getCreated());
    }

    @Test
    void whenAddNewPriceHistoryThenUpdatePriceHistoryThenPriceHistoryRepHasSamePriceHistory() {
        PriceHistory pHistory = new PriceHistory();
        pHistory.setAfterPrice(1000);
        repository.create(pHistory);
        pHistory.setAfterPrice(1500);
        repository.update(pHistory);
        PriceHistory result = repository.findById(pHistory.getId()).get();
        assertThat(result.getAfterPrice()).isEqualTo(pHistory.getAfterPrice());
    }

    @Test
    void whenAddNewPriceHistoryThenDeletePriceHistory() {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setAfterPrice(1000);
        repository.create(priceHistory);
        repository.deleteById(priceHistory.getId());
        Optional<PriceHistory> result = repository.findById(priceHistory.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void whenAddSomePriceHistoriesThenPriceHistoryRepHasSamePriceHistories() {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setAfterPrice(1000);
        PriceHistory priceHistory1 = new PriceHistory();
        priceHistory1.setAfterPrice(2000);
        repository.create(priceHistory);
        repository.create(priceHistory1);
        List<PriceHistory> histories = repository.findAll();
        List<Long> prices = histories.stream()
                .map(PriceHistory::getAfterPrice)
                .collect(Collectors.toList());
        assertThat(prices).containsExactlyInAnyOrder(1000L, 2000L);
    }
}