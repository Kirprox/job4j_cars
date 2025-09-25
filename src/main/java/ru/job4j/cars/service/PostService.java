package ru.job4j.cars.service;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostCreateDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.util.List;

public interface PostService {
    Post save(Post post);

    Post save(PostCreateDto post, FileDto image, User user);

    void updatePost(Post post);

    void deletePostById(Long id, User user);

    PostDto findById(Long id);

    List<PostDto> findAll();

    List<PostDto> findByLastDay();

    List<PostDto> findWithPhoto();

    List<PostDto> findContainsCar(String brand);
}
