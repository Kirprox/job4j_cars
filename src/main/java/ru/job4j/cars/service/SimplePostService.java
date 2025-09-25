package ru.job4j.cars.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostCreateDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {
    private final PostRepository postRepository;
    private final CarRepository carRepository;
    private final EngineRepository engineRepository;
    private final BodyRepository bodyRepository;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final PriceRepository priceRepository;

    @Override
    public Post save(Post post) {
        post.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        return postRepository.create(post);
    }

    @Override
    @Transactional
    public Post save(PostCreateDto postDto, FileDto image, User user) {
        Engine engine = engineRepository.findById(postDto.getEngineId()).get();
        Body body = bodyRepository.findById(postDto.getBodyId()).get();

        Price price = new Price();
        price.setPrice(postDto.getPrice());
        Car car = new Car();
        car.setName(postDto.getName());
        car.setEngine(engine);
        car.setBody(body);
        car.setPrice(price);

        Post post = new Post();
        post.setCar(car);
        post.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        post.setDescription(postDto.getDescription());
        post.setUser(user);
        post.setFile(fileService.save(image));
        return postRepository.create(post);
    }

    @Override
    public void updatePost(Post post) {

    }

    @Override
    @Transactional
    public void deletePostById(Long id, User user) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пост с id=" + id + " не найден"));
        if (!post.getUser().getId().equals(user.getId())) {
            throw new SecurityException("вы не можете удалить чужой пост!");
        }
        postRepository.delete(post);
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
