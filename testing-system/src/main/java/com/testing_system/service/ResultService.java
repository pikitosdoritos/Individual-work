package com.testing_system.service;

import com.testing_system.dto.TestResultResponse;
import com.testing_system.repository.TestResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    private final TestResultRepository testResultRepository;

    public ResultService(TestResultRepository testResultRepository) {
        this.testResultRepository = testResultRepository;
    }

    public List<TestResultResponse> getResultsByTestId(Long testId) {
        return testResultRepository.findByTestId(testId).stream()
                .map(result -> {
                    TestResultResponse response = new TestResultResponse();
                    response.setId(result.getId());
                    response.setStudentName(result.getStudentName());
                    response.setTestId(result.getTestId());
                    response.setScore(result.getScore());
                    response.setTotalQuestions(result.getTotalQuestions());
                    response.setSubmittedAt(result.getSubmittedAt());
                    return response;
                })
                .toList();
    }
}
