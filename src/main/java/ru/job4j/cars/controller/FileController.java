package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.cars.service.FileService;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getById(@PathVariable Long id) {
        var fileOptional = fileService.getFileById(id);
        if (fileOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var file = fileOptional.get();
        String fileName = file.getName().toLowerCase();
        MediaType mediaType = getMediaType(fileName);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(file.getContent());
    }

    private MediaType getMediaType(String fileName) {
        if (fileName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (fileName.endsWith(".webp")) {
            return MediaType.valueOf("image/webp");
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}
