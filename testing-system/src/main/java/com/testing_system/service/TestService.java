package com.testing_system.service;

import com.testing_system.dto.QuestionResponse;
import com.testing_system.dto.TestRequest;
import com.testing_system.dto.TestResponse;
import com.testing_system.exception.ResourceNotFoundException;
import com.testing_system.model.Test;
import com.testing_system.repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<TestResponse> getAllTests() {
        return testRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public TestResponse getTestById(Long id) {
        return toResponse(findOrThrow(id));
    }

    public TestResponse createTest(TestRequest request) {
        Test test = new Test();
        test.setTitle(request.getTitle());
        test.setSubject(request.getSubject());
        return toResponse(testRepository.save(test));
    }

    public TestResponse updateTest(Long id, TestRequest request) {
        Test test = findOrThrow(id);
        test.setTitle(request.getTitle());
        test.setSubject(request.getSubject());
        return toResponse(testRepository.save(test));
    }

    public void deleteTest(Long id) {
        if (!testRepository.existsById(id)) {
            throw new ResourceNotFoundException("Test not found with id: " + id);
        }
        testRepository.deleteById(id);
    }

    private Test findOrThrow(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with id: " + id));
    }

    private TestResponse toResponse(Test test) {
        TestResponse response = new TestResponse();
        response.setId(test.getId());
        response.setTitle(test.getTitle());
        response.setSubject(test.getSubject());
        response.setCreatedAt(test.getCreatedAt());
        response.setQuestions(test.getQuestions().stream()
                .map(q -> {
                    QuestionResponse qr = new QuestionResponse();
                    qr.setId(q.getId());
                    qr.setText(q.getText());
                    qr.setType(q.getType());
                    qr.setOptions(q.getOptions());
                    return qr;
                })
                .toList());
        return response;
    }
}
