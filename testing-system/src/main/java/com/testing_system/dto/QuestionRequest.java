package com.testing_system.dto;

import com.testing_system.model.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class QuestionRequest {

    @NotNull(message = "Test ID is required")
    private Long testId;

    @NotBlank(message = "Question text is required")
    private String text;

    @NotNull(message = "Question type is required")
    private QuestionType type;

    @NotNull(message = "Options are required")
    @Size(min = 2, message = "At least 2 options are required")
    private List<String> options;

    @NotNull(message = "Correct answers are required")
    @Size(min = 1, message = "At least 1 correct answer is required")
    private List<Integer> correctAnswers;

    public Long getTestId() { return testId; }
    public void setTestId(Long testId) { this.testId = testId; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public QuestionType getType() { return type; }
    public void setType(QuestionType type) { this.type = type; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public List<Integer> getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(List<Integer> correctAnswers) { this.correctAnswers = correctAnswers; }
}
