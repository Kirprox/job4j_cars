package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.CarDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.dto.PostFullViewDto;

import java.util.List;

@Service
@AllArgsConstructor
public class SimplePostViewService implements PostViewService {
    private final CarService carService;
    private final PostService postService;

    @Override
    public List<PostFullViewDto> getPostForMainView() {
        return postService.findAll().stream()
                .map(postDto -> {
                    CarDto carDto = carService.findById(postDto.getCarId());
                    return PostFullViewDto.builder()
                            .id(postDto.getId())
                            .name(carDto.getName())
                            .body(carDto.getBody())
                            .price(carDto.getPrice())
                            .engine(carDto.getEngine())
                            .created(postDto.getCreated())
                            .user(postDto.getUser())
                            .description(postDto.getDescription())
                            .fileId(postDto.getFileId())
                            .build();
                }).toList();
    }

    @Override
    public PostFullViewDto getPostForOneView(Long id) {
        PostDto postDto = postService.findById(id);
        CarDto carDto = carService.findById(postDto.getCarId());
        PostFullViewDto result = PostFullViewDto.builder()
                .id(postDto.getId())
                .name(carDto.getName())
                .body(carDto.getBody())
                .price(carDto.getPrice())
                .engine(carDto.getEngine())
                .created(postDto.getCreated())
                .user(postDto.getUser())
                .description(postDto.getDescription()).build();
        return result;
    }
}