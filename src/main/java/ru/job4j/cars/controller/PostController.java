package ru.job4j.cars.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostCreateDto;
import ru.job4j.cars.dto.PostFullViewDto;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.BodyService;
import ru.job4j.cars.service.EngineService;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.service.PostViewService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostViewService postViewService;
    private final EngineService engineService;
    private final BodyService bodyService;
    private final PostService postService;

    @GetMapping("/list")
    public String getListPosts(Model model) {
        List<PostFullViewDto> postList = postViewService.getPostForMainView();
        model.addAttribute("posts", postList);
        model.addAttribute("activePage", "list");
        return "/posts/list";
    }

    @GetMapping("/{id}")
    public String getOnePostPage(Model model, @PathVariable Long id, HttpSession session) {
        PostFullViewDto post = postViewService.getPostForOneView(id);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("post", post);
        return "/posts/one";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable Long id) {
        PostFullViewDto post = postViewService.getPostForOneView(id);
        model.addAttribute("post", post);
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("bodies", bodyService.findAll());
        return "/posts/edit";
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute PostCreateDto post,
                             @RequestParam("file") MultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute("user");

        return "redirect:/posts/list";
    }

    @PostMapping("/create")
    public String savePost(@ModelAttribute PostCreateDto post,
                           @RequestParam("file") MultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            postService.save(post, new FileDto(file.getOriginalFilename(), file.getBytes()), user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "/posts/success";
    }

    @GetMapping("/delete/{id}")
    public String delete(HttpSession session, @PathVariable Long id) {
        User currentUser = (User) session.getAttribute("user");
        postService.deletePostById(id, currentUser);
        return "redirect:/posts/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("bodies", bodyService.findAll());
        return "/posts/create";
    }

}