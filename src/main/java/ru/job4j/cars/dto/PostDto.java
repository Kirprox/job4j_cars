package ru.job4j.cars.dto;

import lombok.Data;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;
    private Long carId;
    private String description;
    private LocalDateTime created;
    private String user;
    private Long fileId;

    public static PostDto fromEntity(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setCarId(post.getCar().getId());
        postDto.setDescription(post.getDescription());
        postDto.setCreated(post.getCreated());
        postDto.setUser(post.getUser().getLogin());
        postDto.setFileId(post.getFile().getId());
        return postDto;
    }
}
