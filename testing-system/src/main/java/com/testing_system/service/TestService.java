package com.testing_system.service;

import com.testing_system.model.TestEntity;
import com.testing_system.repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    private final TestRepository repository;

    public TestService(TestRepository repository) {
        this.repository = repository;
    }

    public List<TestEntity> getAllTests() {
        return repository.findAll();
    }

    public TestEntity getTestById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));
    }

    public TestEntity saveTest(TestEntity test) {
        return repository.save(test);
    }

    public void deleteTest(Long id) {
        repository.deleteById(id);
    }
}