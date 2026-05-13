package com.testing_system.service;

import com.testing_system.exception.ResourceNotFoundException;
import com.testing_system.model.TestResult;
import com.testing_system.repository.TestResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestResultService {

    private final TestResultRepository repository;

    public TestResultService(
            TestResultRepository repository) {

        this.repository = repository;
    }

    public List<TestResult> getAllResults() {
        return repository.findAll();
    }

    public TestResult getResultById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Result not found"));
    }

    public TestResult saveResult(
            TestResult result) {

        return repository.save(result);
    }

    public void deleteResult(Long id) {
        repository.deleteById(id);
    }
}