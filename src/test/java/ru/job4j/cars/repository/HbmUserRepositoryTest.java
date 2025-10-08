package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.exception.UserLoginException;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HbmUserRepositoryTest {
    private static CrudRepository crudRepository;
    private static HbmUserRepository repository;

    @BeforeAll
    static void init() {
        SessionFactory sf = new Configuration()
                .configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        repository = new HbmUserRepository(crudRepository);
    }

    @AfterEach
    void cleanDb() {
        crudRepository.run("DELETE FROM User", Map.of());
    }

    @Test
    void whenAddNewUserThenUserRepHasSameUser() {
        User user = new User();
        user.setLogin("kir");
        repository.create(user);
        User result = repository.findById(user.getId()).get();
        assertThat(result.getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    void whenAddNewUserThenUpdateUserThenUserRepHasSameUser() {
        User user = new User();
        user.setLogin("kir");
        repository.create(user);
        user.setLogin("Andrew");
        repository.update(user);
        User result = repository.findById(user.getId()).get();
        assertThat(result.getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    void whenAddNewUserThenDeleteUser() {
        User user = new User();
        user.setLogin("kir");
        repository.create(user);
        repository.deleteById(user.getId());
        Optional<User> result = repository.findById(user.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void whenAddSomeUsersThenUserRepHasSameUsers() {
        User user = new User();
        user.setLogin("kir");
        User user2 = new User();
        user2.setLogin("Andrew");
        repository.create(user);
        repository.create(user2);
        List<User> users = repository.findAll();
        List<String> logins = users.stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
        assertThat(logins).containsExactlyInAnyOrder("kir", "Andrew");
    }

    @Test
    void whenAddSomeUsersThenUserRepHasSameUsersOrderById() {
        User user = new User();
        user.setLogin("kir");
        User user2 = new User();
        user2.setLogin("Andrew");
        repository.create(user);
        repository.create(user2);
        List<User> users = repository.findAllOrderById();
        List<String> logins = users.stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
        assertThat(logins).containsExactly("kir", "Andrew");
    }

    @Test
    void whenAddSomeUsersThenUserRepHasSameUsersFindByLikeLogin() {
        User user = new User();
        user.setLogin("kir");
        User user2 = new User();
        user2.setLogin("Andrew");
        repository.create(user);
        repository.create(user2);
        List<User> users = repository.findByLikeLogin("ir");
        List<String> logins = users.stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
        assertThat(logins).containsExactly("kir");
    }

    @Test
    void whenAddSomeUsersThenUserRepHasSameUsersFindByLogin() {
        User user = new User();
        user.setLogin("kir");
        User user2 = new User();
        user2.setLogin("Andrew");
        repository.create(user);
        repository.create(user2);
        User result = repository.findByLogin("kir").get();
        assertThat(result.getLogin()).isEqualTo("kir");
    }

    @Test
    void whenUserNotFoundThenThrowsException() {
        assertThrows(UserLoginException.class, () ->
                repository.findByLoginAndPassword("wrong", "wrongPass")
        );
    }
}