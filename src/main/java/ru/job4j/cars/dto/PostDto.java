package ru.job4j.cars.dto;

import lombok.Data;
import ru.job4j.cars.model.File;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private String description;
    private LocalDateTime created;
    private String user;
    private CarDto carDto;
    private File file;
}
