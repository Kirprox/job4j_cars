package ru.job4j.cars.service;

import ru.job4j.cars.dto.PostFullViewDto;

import java.util.List;

public interface PostViewService {
    List<PostFullViewDto> getPostForMainView();

    PostFullViewDto getPostForOneView(Long id);
}