package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.service.PostService;

import java.util.List;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public String getListPosts() {
        List<Post> postList = postService.findAll();
        System.out.println(postList);
        return "/posts/list";
    }
}
