package ru.job4j.cars.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.repository.FileRepository;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleFileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @TempDir
    Path tempDir;

    private SimpleFileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new SimpleFileService(fileRepository, tempDir.toString());
    }

    @Test
    void whenSaveFileThenItIsCreatedOnDiskAndSavedInRepo() throws IOException {
        FileDto dto = new FileDto("test.txt", "hello".getBytes());
        File savedFile = new File();
        savedFile.setId(1L);
        savedFile.setName(dto.getName());
        savedFile.setPath(tempDir.resolve("any").toString());

        when(fileRepository.create(any(File.class))).thenReturn(savedFile);

        File result = fileService.save(dto);

        assertThat(result.getName()).isEqualTo("test.txt");
        verify(fileRepository).create(any(File.class));

        assertThat(Files.list(tempDir)).isNotEmpty();
        String content = Files.readString(Files.list(tempDir).findFirst().get());
        assertThat(content).isEqualTo("hello");
    }

    @Test
    void whenGetFileByIdThenReturnFileDto() throws IOException {
        Path filePath = tempDir.resolve("stored.txt");
        Files.write(filePath, "data".getBytes());

        File file = new File();
        file.setId(1L);
        file.setName("stored.txt");
        file.setPath(filePath.toString());

        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));

        Optional<FileDto> result = fileService.getFileById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("stored.txt");
        assertThat(new String(result.get().getContent())).isEqualTo("data");
    }

    @Test
    void whenDeleteByIdThenFileDeletedFromDiskAndRepo() throws IOException {
        Path filePath = tempDir.resolve("delete.txt");
        Files.write(filePath, "bye".getBytes());

        File file = new File();
        file.setId(5L);
        file.setPath(filePath.toString());

        when(fileRepository.findById(5L)).thenReturn(Optional.of(file));

        fileService.deleteById(5L);

        assertThat(Files.exists(filePath)).isFalse();
        verify(fileRepository).deleteById(5L);
    }
}