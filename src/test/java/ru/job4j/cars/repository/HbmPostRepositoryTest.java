package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HbmPostRepositoryTest {
    private static CrudRepository crudRepository;
    private static PostRepository repository;
    private static HbmCarRepository carRepository;
    private static HbmFileRepository fileRepository;

    @BeforeAll
    static void init() {
        SessionFactory sf = new Configuration()
                .configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        repository = new HbmPostRepository(crudRepository);
        carRepository = new HbmCarRepository(crudRepository);
        fileRepository = new HbmFileRepository(crudRepository);
    }

    @AfterEach
    void clearDb() {
        crudRepository.run("DELETE FROM Post", Map.of());
    }

    @Test
    void whenAddNewPOSTThenPostRepHasSamePost() {
        Post post = new Post();
        post.setDescription("usual car");
        repository.create(post);
        Post result = repository.findById(post.getId()).get();
        assertThat(result.getDescription()).isEqualTo(post.getDescription());
    }

    @Test
    void whenAddNewPostThenUpdatePostThenPostRepHasSamePost() {
        Post post = new Post();
        post.setDescription("usual car");
        repository.create(post);
        post.setDescription("new car");
        repository.update(post);
        Post result = repository.findById(post.getId()).get();
        assertThat(result.getDescription()).isEqualTo(post.getDescription());
    }

    @Test
    void whenAddNewPostThenDeletePost() {
        Post post = new Post();
        post.setDescription("usual car");
        repository.create(post);
        repository.deleteById(post.getId());
        Optional<Post> result = repository.findById(post.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void whenAddSomePostsThenPostRepHasSamePosts() {
        Post post = new Post();
        post.setDescription("usual car");
        Post post2 = new Post();
        post2.setDescription("car");
        repository.create(post);
        repository.create(post2);
        List<Post> posts = repository.findAll();
        List<String> results = posts.stream()
                .map(Post::getDescription)
                .collect(Collectors.toList());
        assertThat(results).containsExactlyInAnyOrder("usual car", "car");
    }

    @Test
    void whenAddSomePostThenPostRepHasFindByLastDayPosts() {
        Post post = new Post();
        Post post2 = new Post();
        Post post3 = new Post();
        post.setDescription("usual car");
        post2.setDescription("car");
        post3.setDescription("car3");
        post.setCreated(LocalDateTime.now());
        post2.setCreated(LocalDateTime.now());
        post3.setCreated(LocalDateTime.now().minusWeeks(1));
        repository.create(post);
        repository.create(post2);
        List<Post> posts = repository.findByLastDay();
        List<String> results = posts.stream()
                .map(Post::getDescription)
                .collect(Collectors.toList());
        assertThat(results).containsExactlyInAnyOrder("usual car", "car");
    }

    @Test
    void whenAddSomePostsThenPostRepHasFindWithPhotoPost() {
        Post post = new Post();
        Post post2 = new Post();
        File file = new File();
        file.setPath("somePath");
        post.setDescription("usual car");
        post2.setDescription("car");
        post.setFile(file);
        fileRepository.create(file);
        repository.create(post);
        repository.create(post2);
        List<Post> posts = repository.findWithPhoto();
        List<String> results = posts.stream()
                .map(Post::getDescription)
                .collect(Collectors.toList());
        assertThat(results).containsExactlyInAnyOrder("usual car")
                .doesNotContain("car");
    }

    @Test
    void whenAddSomeHistoriesThenEngineRepHasFindContainsCarBrandHistories() {
        Post post = new Post();
        Post post2 = new Post();
        Car car = new Car();
        Car car1 = new Car();
        car.setName("toyota");
        car1.setName("kamaz");
        post.setCar(car);
        post2.setCar(car1);
        post.setDescription("usual car");
        post2.setDescription("car");
        carRepository.create(car);
        carRepository.create(car1);
        repository.create(post);
        repository.create(post2);
        List<Post> posts = repository.findContainsCarBrand("toyota");
        List<String> results = posts.stream()
                .map(Post::getDescription)
                .collect(Collectors.toList());
        assertThat(results).containsExactlyInAnyOrder("usual car")
                .doesNotContain("car");
    }
}