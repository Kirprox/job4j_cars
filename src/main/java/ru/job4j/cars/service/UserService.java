package ru.job4j.cars.service;

import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);

    void update(User user);

    void deleteById(Long id);

    List<User> findAll();

    List<User> findAllOrderById();

    Optional<User> findById(Long userId);

    List<User> findByLikeLogin(String key);

    Optional<User> findByLogin(String login);

    Optional<User> findByLoginAndPassword(String login, String password);
}