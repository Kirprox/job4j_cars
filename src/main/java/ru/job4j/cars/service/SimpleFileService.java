package ru.job4j.cars.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class SimpleFileService implements FileService {
    private final FileRepository fileRepository;

    @Value("${file.directory}")
    private final String storageDirectory;

    @PostConstruct
    private void init() {
        createStorageDirectory(storageDirectory);
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File save(FileDto fileDto) {
        var path = getNewFilePath(fileDto.getName());
        writeFileBites(path, fileDto.getContent());
        File file = new File();
        file.setName(fileDto.getName());
        file.setPath(path);
        return fileRepository.create(file);
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + "_" + sourceName;
    }

    private void writeFileBites(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<FileDto> getFileById(Long id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        FileDto dto = new FileDto();
        dto.setName(fileOptional.get().getName());
        dto.setContent(content);
        return Optional.of(dto);
    }

    @Override
    public void deleteById(Long id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isPresent()) {
            deleteFile(fileOptional.get().getPath());
            fileRepository.deleteById(id);
        }
    }
}