package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
    public PostDto findById(Long id) {
        return postRepository.findById(id)
                .map(PostDto::fromEntity).get();
    }

    @Override
    public List<PostDto> findAll() {
        return postRepository.findAll().stream()
                .map(PostDto::fromEntity).toList();
    }

    @Override
    public List<PostDto> findByLastDay() {
        return postRepository.findByLastDay().stream()
                .map(PostDto::fromEntity).toList();
    }

    @Override
    public List<PostDto> findWithPhoto() {
        return postRepository.findWithPhoto().stream()
                .map(PostDto::fromEntity).toList();
    }

    @Override
    public List<PostDto> findContainsCar(String brand) {
        return postRepository.findContainsCarBrand(brand).stream()
                .map(PostDto::fromEntity).toList();
    }
}
