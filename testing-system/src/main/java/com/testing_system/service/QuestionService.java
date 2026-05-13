package com.testing_system.service;

import com.testing_system.exception.ResourceNotFoundException;
import com.testing_system.model.Question;
import com.testing_system.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public List<Question> getAllQuestions() {
        return repository.findAll();
    }

    public Question getQuestionById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Question not found"));
    }

    public Question saveQuestion(Question question) {
        return repository.save(question);
    }

    public void deleteQuestion(Long id) {
        repository.deleteById(id);
    }
}