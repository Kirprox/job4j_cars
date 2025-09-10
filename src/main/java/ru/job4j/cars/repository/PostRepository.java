package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post create(Post post);

    void update(Post post);

    void deleteById(Long id);

    Optional<Post> findById(Long id);

    List<Post> findAll();

    List<Post> findByLastDay();

    List<Post> findWithPhoto();

    List<Post> findContainsCarBrand(String brand);

}
