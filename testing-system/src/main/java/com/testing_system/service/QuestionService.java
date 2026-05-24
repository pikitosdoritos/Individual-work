package com.testing_system.service;

import com.testing_system.dto.QuestionRequest;
import com.testing_system.dto.QuestionResponse;
import com.testing_system.exception.ResourceNotFoundException;
import com.testing_system.model.Question;
import com.testing_system.model.Test;
import com.testing_system.repository.QuestionRepository;
import com.testing_system.repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    public QuestionService(QuestionRepository questionRepository, TestRepository testRepository) {
        this.questionRepository = questionRepository;
        this.testRepository = testRepository;
    }

    public List<QuestionResponse> getQuestionsByTestId(Long testId) {
        return questionRepository.findByTestId(testId).stream()
                .map(this::toResponse)
                .toList();
    }

    public QuestionResponse createQuestion(QuestionRequest request) {
        validateQuestionRequest(request);
        Test test = testRepository.findById(request.getTestId())
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with id: " + request.getTestId()));
        Question question = new Question();
        question.setText(request.getText());
        question.setType(request.getType());
        question.setOptions(request.getOptions());
        question.setCorrectAnswers(request.getCorrectAnswers());
        question.setTest(test);
        return toResponse(questionRepository.save(question));
    }

    public QuestionResponse updateQuestion(Long id, QuestionRequest request) {
        validateQuestionRequest(request);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        question.setText(request.getText());
        question.setType(request.getType());
        question.setOptions(request.getOptions());
        question.setCorrectAnswers(request.getCorrectAnswers());
        return toResponse(questionRepository.save(question));
    }

    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
    }

    private QuestionResponse toResponse(Question question) {
        QuestionResponse response = new QuestionResponse();
        response.setId(question.getId());
        response.setText(question.getText());
        response.setType(question.getType());
        response.setOptions(question.getOptions());
        return response;
    }

    private void validateQuestionRequest(QuestionRequest request) {
        if (request.getType() == com.testing_system.model.QuestionType.SINGLE && request.getCorrectAnswers().size() != 1) {
            throw new IllegalArgumentException("Single choice question must have exactly one correct answer.");
        }
        for (Integer index : request.getCorrectAnswers()) {
            if (index < 0 || index >= request.getOptions().size()) {
                throw new IllegalArgumentException("Correct answer index out of bounds: " + index);
            }
        }
        if (request.getCorrectAnswers().stream().distinct().count() != request.getCorrectAnswers().size()) {
            throw new IllegalArgumentException("Correct answers contain duplicates.");
        }
    }
}
