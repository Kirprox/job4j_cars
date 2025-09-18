package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {
    private final PostRepository postRepository;

    @Override
    public Post save(Post post) {
        post.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        return postRepository.create(post);
    }

    @Override
    public void updatePost(Post post) {
        postRepository.update(post);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Optional<PostDto> findById(Long id) {
        //todo дописать методы. преобразовать пост в постдто
        return Optional.empty();
    }

    @Override
    public List<PostDto> findAll() {
        return null;
    }

    @Override
    public List<PostDto> findByLastDay() {
        return null;
    }

    @Override
    public List<PostDto> findWithPhoto() {
        return null;
    }

    @Override
    public List<PostDto> findContainsCar(String brand) {
        return null;
    }
}
