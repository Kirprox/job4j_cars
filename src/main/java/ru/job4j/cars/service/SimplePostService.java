package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {
    private final PostRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.create(post);
    }

    @Override
    public void updatePost(Post post) {
postRepository.update(post);
    }

    @Override
    public void deletePostById(int id) {
postRepository.deleteById(id);
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findAdd() {
        return postRepository.findAll();
    }
}
