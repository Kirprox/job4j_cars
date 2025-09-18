package ru.job4j.cars.dto;

import lombok.Data;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private String description;
    private LocalDateTime created;
    private String user;

    public static PostDto fromEntity(Post post) {
        PostDto postDto = new PostDto();
        postDto.setDescription(post.getDescription());
        postDto.setCreated(post.getCreated());
        postDto.setUser(post.getUser().getLogin());
        return postDto;
    }
}
