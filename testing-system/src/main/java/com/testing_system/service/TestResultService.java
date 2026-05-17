package com.testing_system.service;

import com.testing_system.dto.SubmitRequest;
import com.testing_system.dto.TestResultResponse;
import com.testing_system.exception.ResourceNotFoundException;
import com.testing_system.model.Question;
import com.testing_system.model.Test;
import com.testing_system.model.TestResult;
import com.testing_system.repository.TestRepository;
import com.testing_system.repository.TestResultRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TestResultService {

    private final TestResultRepository testResultRepository;
    private final TestRepository testRepository;

    public TestResultService(TestResultRepository testResultRepository, TestRepository testRepository) {
        this.testResultRepository = testResultRepository;
        this.testRepository = testRepository;
    }

    public TestResultResponse submitTest(SubmitRequest request) {
        Test test = testRepository.findById(request.getTestId())
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with id: " + request.getTestId()));

        int score = 0;
        for (Question question : test.getQuestions()) {
            List<Integer> submitted = request.getAnswers().getOrDefault(question.getId(), List.of());
            Set<Integer> submittedSet = new HashSet<>(submitted);
            Set<Integer> correctSet = new HashSet<>(question.getCorrectAnswers());
            if (submittedSet.equals(correctSet)) {
                score++;
            }
        }

        TestResult result = new TestResult();
        result.setStudentName(request.getStudentName());
        result.setTestId(request.getTestId());
        result.setScore(score);
        result.setTotalQuestions(test.getQuestions().size());

        return toResponse(testResultRepository.save(result));
    }

    private TestResultResponse toResponse(TestResult result) {
        TestResultResponse response = new TestResultResponse();
        response.setId(result.getId());
        response.setStudentName(result.getStudentName());
        response.setTestId(result.getTestId());
        response.setScore(result.getScore());
        response.setTotalQuestions(result.getTotalQuestions());
        response.setSubmittedAt(result.getSubmittedAt());
        return response;
    }
}
