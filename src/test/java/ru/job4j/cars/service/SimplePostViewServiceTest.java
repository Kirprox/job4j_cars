package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.cars.dto.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimplePostViewServiceTest {

    @Mock
    private CarService carService;
    @Mock
    private PostService postService;

    @InjectMocks
    private SimplePostViewService postViewService;

    @Test
    void whenGetPostForMainViewThenReturnListOfFullDtos() {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setCarId(10L);
        postDto.setDescription("Fast car");
        postDto.setCreated(LocalDateTime.now());

        CarDto carDto = new CarDto();
        carDto.setName("BMW");
        carDto.setBody("sedan");
        carDto.setEngine("V8");
        carDto.setPrice(10000L);

        when(postService.findAll()).thenReturn(List.of(postDto));
        when(carService.findById(10L)).thenReturn(carDto);

        List<PostFullViewDto> result = postViewService.getPostForMainView();

        assertThat(result).hasSize(1);
        PostFullViewDto viewDto = result.get(0);
        assertThat(viewDto.getName()).isEqualTo("BMW");
        assertThat(viewDto.getBody()).isEqualTo("sedan");
        assertThat(viewDto.getDescription()).isEqualTo("Fast car");
        verify(postService).findAll();
        verify(carService).findById(10L);
    }

    @Test
    void whenGetPostForOneViewThenReturnFullDto() {
        PostDto postDto = new PostDto();
        postDto.setId(2L);
        postDto.setCarId(20L);
        postDto.setDescription("One post");

        CarDto carDto = new CarDto();
        carDto.setName("Audi");
        carDto.setBody("coupe");
        carDto.setEngine("V6");
        carDto.setPrice(20000L);

        when(postService.findById(2L)).thenReturn(postDto);
        when(carService.findById(20L)).thenReturn(carDto);

        PostFullViewDto result = postViewService.getPostForOneView(2L);

        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("Audi");
        assertThat(result.getBody()).isEqualTo("coupe");
        assertThat(result.getDescription()).isEqualTo("One post");

        verify(postService).findById(2L);
        verify(carService).findById(20L);
    }
}