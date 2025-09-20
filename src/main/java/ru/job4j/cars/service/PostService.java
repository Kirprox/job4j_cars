package ru.job4j.cars.service;

import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;

import java.util.List;

public interface PostService {
    Post save(Post post);

    void updatePost(Post post);

    void deletePostById(Long id);

    PostDto findById(Long id);

    List<PostDto> findAll();

    List<PostDto> findByLastDay();

    List<PostDto> findWithPhoto();

    List<PostDto> findContainsCar(String brand);
}
