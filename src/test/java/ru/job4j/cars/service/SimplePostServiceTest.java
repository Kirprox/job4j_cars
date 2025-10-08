package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostCreateDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.BodyRepository;
import ru.job4j.cars.repository.EngineRepository;
import ru.job4j.cars.repository.PostRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimplePostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private EngineRepository engineRepository;
    @Mock
    private BodyRepository bodyRepository;
    @Mock
    private FileService fileService;

    @InjectMocks
    private SimplePostService postService;

    @Test
    void whenSaveDtoThenPostCreated() {
        PostCreateDto dto = new PostCreateDto();
        dto.setName("Car");
        dto.setEngineId(1L);
        dto.setBodyId(2L);
        dto.setPrice(10L);
        dto.setDescription("Fast");

        User user = new User();
        user.setId(10L);

        Engine engine = new Engine();
        Body body = new Body();

        when(engineRepository.findById(1L)).thenReturn(Optional.of(engine));
        when(bodyRepository.findById(2L)).thenReturn(Optional.of(body));
        when(fileService.save(any())).thenReturn(new File());
        when(postRepository.create(any())).thenAnswer(inv -> inv.getArgument(0));

        Post result = postService.save(dto, new FileDto("img.png", new byte[0]), user);

        assertThat(result.getCar().getEngine()).isEqualTo(engine);
        assertThat(result.getUser()).isEqualTo(user);
        verify(postRepository).create(any(Post.class));
    }

    @Test
    void whenDeleteNotOwnerThenThrowException() {
        Post post = new Post();
        User owner = new User(); owner.setId(1L);
        post.setUser(owner);

        when(postRepository.findById(5L)).thenReturn(Optional.of(post));

        User other = new User(); other.setId(2L);

        assertThatThrownBy(() -> postService.deletePostById(5L, other))
                .isInstanceOf(SecurityException.class);
    }
}