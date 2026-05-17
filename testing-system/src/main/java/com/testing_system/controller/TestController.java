package com.testing_system.controller;

import com.testing_system.dto.TestRequest;
import com.testing_system.dto.TestResponse;
import com.testing_system.service.TestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public List<TestResponse> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/{id}")
    public TestResponse getTestById(@PathVariable Long id) {
        return testService.getTestById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestResponse createTest(@Valid @RequestBody TestRequest request) {
        return testService.createTest(request);
    }

    @PutMapping("/{id}")
    public TestResponse updateTest(@PathVariable Long id, @Valid @RequestBody TestRequest request) {
        return testService.updateTest(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
    }
}
