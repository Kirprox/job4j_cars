package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post save(Post post);

    void updatePost(Post post);

    void deletePostById(Long id);

    Optional<Post> findById(Long id);

    List<Post> findAdd();

    List<Post> findByLastDay();

    List<Post> findWithPhoto();

    List<Post> findContainsCar(String brand);
}
