package ru.job4j.cars.dto;

import lombok.Data;

@Data
public class CarDto {
    private String name;
    private String body;
    private Long price;
    private String engine;

}
