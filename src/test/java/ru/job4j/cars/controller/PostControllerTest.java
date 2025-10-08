package ru.job4j.cars.controller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostCreateDto;
import ru.job4j.cars.dto.PostFullViewDto;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.BodyService;
import ru.job4j.cars.service.EngineService;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.service.PostViewService;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {
    private PostViewService postViewService;
    private EngineService engineService;
    private BodyService bodyService;
    private PostService postService;
    private PostController postController;

    @BeforeEach
    void init() {
        postViewService = mock(PostViewService.class);
        engineService = mock(EngineService.class);
        bodyService = mock(BodyService.class);
        postService = mock(PostService.class);
        postController = new PostController(postViewService, engineService, bodyService, postService);
    }

    @Test
    void whenGetListPostsThenReturnListView() {
        List<PostFullViewDto> posts = List.of(
                PostFullViewDto.builder()
                        .id(1L)
                        .name("Post1")
                        .description("desc")
                        .build()
        );

        when(postViewService.getPostForMainView()).thenReturn(posts);

        Model model = new ConcurrentModel();
        String view = postController.getListPosts(model);

        assertThat(view).isEqualTo("/posts/list");
        assertThat(model.getAttribute("posts")).isEqualTo(posts);
        assertThat(model.getAttribute("activePage")).isEqualTo("list");
    }

    @Test
    void whenGetOnePostThenReturnOneView() {
        PostFullViewDto post = PostFullViewDto.builder()
                .id(1L)
                .name("Post1")
                .description("desc")
                .build();
        User user = new User();
        user.setId(1L);
        user.setLogin("user");
        user.setPassword("pass");

        when(postViewService.getPostForOneView(1L)).thenReturn(post);

        Model model = new ConcurrentModel();
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);

        String view = postController.getOnePostPage(model, 1L, session);

        assertThat(view).isEqualTo("/posts/one");
        assertThat(model.getAttribute("post")).isEqualTo(post);
        assertThat(model.getAttribute("user")).isEqualTo(user);
    }

    @Test
    void whenGetEditPageThenReturnEditView() {
        PostFullViewDto post = PostFullViewDto.builder()
                .id(1L)
                .name("Post1")
                .description("desc")
                .build();

        post.setId(1L);
        post.setName("Post1");
        post.setBody("desc");
        Engine engine1 = new Engine();
        engine1.setName("Engine1");
        Body body1 = new Body();
        body1.setName("Body1");
        List<Engine> engines = List.of(engine1);
        List<Body> bodies = List.of(body1);

        when(postViewService.getPostForOneView(1L)).thenReturn(post);
        when(engineService.findAll()).thenReturn(engines);
        when(bodyService.findAll()).thenReturn(bodies);

        Model model = new ConcurrentModel();
        String view = postController.getEditPage(model, 1L);

        assertThat(view).isEqualTo("/posts/edit");
        assertThat(model.getAttribute("post")).isEqualTo(post);
        assertThat(model.getAttribute("engines")).isEqualTo(engines);
        assertThat(model.getAttribute("bodies")).isEqualTo(bodies);
    }

    @Test
    void whenSavePostThenReturnSuccess() throws IOException {
        PostCreateDto post = new PostCreateDto();
        post.setName("Title");
        post.setDescription("Desc");

        User user = new User();
        user.setId(1L);
        user.setLogin("user");
        user.setPassword("pass");
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("file.txt");
        when(file.getBytes()).thenReturn("content".getBytes());

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);

        String view = postController.savePost(post, file, session);

        verify(postService).save(eq(post), any(FileDto.class), eq(user));
        assertThat(view).isEqualTo("/posts/success");
    }

    @Test
    void whenDeletePostThenRedirectToList() {
        User user = new User();
        user.setId(1L);
        user.setLogin("user");
        user.setPassword("pass");
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);

        String view = postController.delete(session, 1L);

        verify(postService).deletePostById(1L, user);
        assertThat(view).isEqualTo("redirect:/posts/list");
    }

    @Test
    void whenGetCreationPageThenReturnCreateView() {
        Engine engine1 = new Engine();
        engine1.setName("Engine1");
        Body body1 = new Body();
        body1.setName("Body1");
        List<Engine> engines = List.of(engine1);
        List<Body> bodies = List.of(body1);

        when(engineService.findAll()).thenReturn(engines);
        when(bodyService.findAll()).thenReturn(bodies);

        Model model = new ConcurrentModel();
        String view = postController.getCreationPage(model);

        assertThat(view).isEqualTo("/posts/create");
        assertThat(model.getAttribute("engines")).isEqualTo(engines);
        assertThat(model.getAttribute("bodies")).isEqualTo(bodies);
    }
}