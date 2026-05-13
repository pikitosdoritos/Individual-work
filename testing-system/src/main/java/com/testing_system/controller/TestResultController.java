package com.testing_system.controller;

import com.testing_system.model.TestResult;
import com.testing_system.service.TestResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class TestResultController {

    private final TestResultService service;

    public TestResultController(
            TestResultService service) {

        this.service = service;
    }

    @GetMapping
    public List<TestResult> getAllResults() {
        return service.getAllResults();
    }

    @GetMapping("/{id}")
    public TestResult getResultById(
            @PathVariable Long id) {

        return service.getResultById(id);
    }

    @PostMapping
    public TestResult createResult(
            @RequestBody TestResult result) {

        return service.saveResult(result);
    }

    @DeleteMapping("/{id}")
    public String deleteResult(
            @PathVariable Long id) {

        service.deleteResult(id);

        return "Result deleted successfully";
    }
}