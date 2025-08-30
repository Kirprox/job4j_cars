package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    @Override
    public Post create(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    @Override
    public void deleteById(int id) {
        crudRepository.query(
                "FROM Post WHERE id = :fId", Post.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "FROM Post Where id = :fId", Post.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Post> findAll() {
        return crudRepository.query(
                "FROM Post", Post.class
        );
    }
}
