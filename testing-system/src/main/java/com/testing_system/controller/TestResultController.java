package com.testing_system.controller;

import com.testing_system.dto.SubmitRequest;
import com.testing_system.dto.TestResultResponse;
import com.testing_system.service.ResultService;
import com.testing_system.service.TestResultService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class TestResultController {

    private final TestResultService testResultService;
    private final ResultService resultService;

    public TestResultController(TestResultService testResultService, ResultService resultService) {
        this.testResultService = testResultService;
        this.resultService = resultService;
    }

    @PostMapping("/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public TestResultResponse submitTest(@Valid @RequestBody SubmitRequest request) {
        return testResultService.submitTest(request);
    }

    @GetMapping("/test/{testId}")
    public List<TestResultResponse> getResultsByTestId(@PathVariable Long testId) {
        return resultService.getResultsByTestId(testId);
    }
}
