package ru.job4j.cars.dto;

import lombok.Data;

@Data
public class PostCreateDto {
    String name;
    String description;
    Long bodyId;
    Long engineId;
    Long price;
}