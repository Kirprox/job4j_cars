package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.exception.UserActionException;
import ru.job4j.cars.exception.UserLoginException;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public User create(User user) {
        if (existsByLogin(user.getLogin())) {
            throw new UserActionException("Пользователь с таким логином уже существует");
        }
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    @Override
    public boolean existsByLogin(String login) {
        Long count = crudRepository.optional(
                "SELECT COUNT(u) FROM User u WHERE u.login = :fLogin", Long.class,
                Map.of("fLogin", login)
        ).orElse(0L);
        return count > 0;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    @Override
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }


    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    @Override
    public void deleteById(Long userId) {
        crudRepository.run(
                "delete from User where id = :fId",
                Map.of("fId", userId)
        );
    }

    @Override
    public List<User> findAll() {
        return crudRepository.query(
                "FROM User", User.class
        );
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    @Override
    public List<User> findAllOrderById() {
        return crudRepository.query("from User order by id asc", User.class);
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(Long userId) {
        return crudRepository.optional(
                "from User where id = :fId", User.class,
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    @Override
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                "from User where login like :fKey", User.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                "from User where login = :fLogin", User.class,
                Map.of("fLogin", login)
        );
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {

        return crudRepository.optional(
                "FROM User where login = :fLogin AND password = :fPassword", User.class,
                Map.of("fLogin", login, "fPassword", password)
        ).orElseThrow(() -> new UserLoginException("Неверный логин или пароль"));

    }

}