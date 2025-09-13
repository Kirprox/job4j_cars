package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findByLastDay() {
        return postRepository.findByLastDay();
    }

    @Override
    public List<Post> findWithPhoto() {
        return postRepository.findWithPhoto();
    }

    @Override
    public List<Post> findContainsCar(String brand) {
        return postRepository.findContainsCarBrand(brand);
    }
}
