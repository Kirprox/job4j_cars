package ru.job4j.cars.dto;

import lombok.Data;
import ru.job4j.cars.model.Car;

@Data
public class CarDto {
    private String name;
    private String body;
    private Long price;
    private String engine;

    //todo написать метод для преобразования кар в дто
    public static CarDto fromEntity(Car car) {
        CarDto dto = new CarDto();
        dto.setName(car.getName());
        dto.setBody(car.getBody().getName());
        dto.setPrice(car.getPrice().getPrice());
        dto.setEngine(car.getEngine().getName());
        return dto;
    }
}
