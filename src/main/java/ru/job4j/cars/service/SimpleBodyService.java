package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.repository.BodyRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleBodyService implements BodyService {
    BodyRepository bodyRepository;

    @Override
    public Body save(Body body) {
        return bodyRepository.create(body);
    }

    @Override
    public void update(Body body) {
        bodyRepository.update(body);
    }

    @Override
    public void deleteById(Long id) {
        bodyRepository.deleteById(id);
    }

    @Override
    public Optional<Body> findById(Long id) {
        return bodyRepository.findById(id);
    }

    @Override
    public List<Body> findAll() {
        return bodyRepository.findAll();
    }
}
