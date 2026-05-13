package com.testing_system.controller;

import com.testing_system.model.TestEntity;
import com.testing_system.service.TestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {

    private final TestService service;

    public TestController(TestService service) {
        this.service = service;
    }

    @GetMapping
    public List<TestEntity> getAllTests() {

        return service.getAllTests();
    }

    @GetMapping("/{id}")
    public TestEntity getTestById(
            @PathVariable Long id) {

        return service.getTestById(id);
    }

    @PostMapping
    public TestEntity createTest(
            @RequestBody TestEntity test) {

        return service.saveTest(test);
    }

    @DeleteMapping("/{id}")
    public String deleteTest(
            @PathVariable Long id) {

        service.deleteTest(id);

        return "Test deleted successfully";
    }
}