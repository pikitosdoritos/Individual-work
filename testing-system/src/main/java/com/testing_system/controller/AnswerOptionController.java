package com.testing_system.controller;

import com.testing_system.model.AnswerOption;
import com.testing_system.service.AnswerOptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answer-options")
public class AnswerOptionController {

    private final AnswerOptionService service;

    public AnswerOptionController(
            AnswerOptionService service) {

        this.service = service;
    }

    @GetMapping
    public List<AnswerOption> getAllAnswerOptions() {

        return service.getAllAnswerOptions();
    }

    @GetMapping("/{id}")
    public AnswerOption getAnswerOptionById(
            @PathVariable Long id) {

        return service.getAnswerOptionById(id);
    }

    @PostMapping
    public AnswerOption createAnswerOption(
            @RequestBody AnswerOption answerOption) {

        return service.saveAnswerOption(answerOption);
    }

    @DeleteMapping("/{id}")
    public String deleteAnswerOption(
            @PathVariable Long id) {

        service.deleteAnswerOption(id);

        return "Answer option deleted successfully";
    }
}