package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleEngineService implements EngineService {
    private final EngineRepository engineRepository;

    @Override
    public Engine create(Engine engine) {
        return engineRepository.create(engine);
    }

    @Override
    public void update(Engine engine) {
        engineRepository.update(engine);
    }

    @Override
    public void deleteById(int id) {
        engineRepository.deleteById(id);
    }

    @Override
    public Optional<Engine> findById(int id) {
        return engineRepository.findById(id);
    }

    @Override
    public List<Engine> findAll() {
        return engineRepository.findAll();
    }
}
