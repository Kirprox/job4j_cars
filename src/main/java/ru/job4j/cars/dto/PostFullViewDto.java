package ru.job4j.cars.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Builder
public class PostFullViewDto {
    private Long id;
    private String name;
    private String body;
    private Long price;
    private String engine;
    private LocalDateTime created;
    private String user;
    private String description;
}
