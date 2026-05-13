package com.testing_system.controller;

import com.testing_system.model.Question;
import com.testing_system.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(
            QuestionService service) {

        this.service = service;
    }

    @GetMapping
    public List<Question> getAllQuestions() {

        return service.getAllQuestions();
    }

    @GetMapping("/{id}")
    public Question getQuestionById(
            @PathVariable Long id) {

        return service.getQuestionById(id);
    }

    @PostMapping
    public Question createQuestion(
            @RequestBody Question question) {

        return service.saveQuestion(question);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(
            @PathVariable Long id) {

        service.deleteQuestion(id);

        return "Question deleted successfully";
    }
}