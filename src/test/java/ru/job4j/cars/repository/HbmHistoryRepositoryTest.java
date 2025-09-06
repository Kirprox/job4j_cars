package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.History;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HbmHistoryRepositoryTest {
    private static CrudRepository crudRepository;
    private static HbmHistoryRepository repository;
    private static LocalDateTime date;

    @BeforeAll
    static void init() {
        SessionFactory sf = new Configuration()
                .configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        repository = new HbmHistoryRepository(crudRepository);
        date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    @AfterEach
    void cleanDb() {
        crudRepository.run("DELETE FROM History", Map.of());
    }

    @Test
    void whenAddNewHistoryThenHistoryRepHasSameHistory() {
        History history = new History();
        history.setStarAt(date);
        repository.save(history);
        History result = repository.findById(history.getId()).get();
        assertThat(result.getStarAt()).isEqualTo(history.getStarAt());
    }

    @Test
    void whenAddNewHistoryThenUpdateHistoryThenHistoryRepHasSameHistory() {
        History history = new History();
        history.setStarAt(date);
        repository.save(history);
        history.setStarAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        repository.update(history);
        History result = repository.findById(history.getId()).get();
        assertThat(result.getStarAt()).isEqualTo(history.getStarAt());
    }

    @Test
    void whenAddNewHistoryThenDeleteHistory() {
        History history = new History();
        history.setStarAt(date);
        repository.save(history);
        repository.deleteById(history.getId());
        Optional<History> result = repository.findById(history.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void whenAddSomeHistoriesThenHistoryRepHasSameHistories() {
        History history1 = new History();
        history1.setStarAt(date);
        History history2 = new History();
        history2.setStarAt(date);
        repository.save(history1);
        repository.save(history2);
        List<History> histories = repository.findAll();
        List<LocalDateTime> dates = histories.stream()
                .map(History::getStarAt)
                .collect(Collectors.toList());
        assertThat(dates).containsExactlyInAnyOrder(date, date);

    }
}