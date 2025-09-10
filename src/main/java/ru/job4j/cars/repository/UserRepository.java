package ru.job4j.cars.repository;

import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User create(User user);

    void update(User user);

    void deleteById(Long id);

    List<User> findAll();

    List<User> findAllOrderById();

    Optional<User> findById(Long id);

    List<User> findByLikeLogin(String key);

    Optional<User> findByLogin(String login);
}
