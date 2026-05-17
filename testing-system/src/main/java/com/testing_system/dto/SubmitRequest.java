package com.testing_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public class SubmitRequest {

    @NotBlank(message = "Student name is required")
    private String studentName;

    @NotNull(message = "Test ID is required")
    private Long testId;

    @NotNull(message = "Answers are required")
    private Map<Long, List<Integer>> answers;

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Long getTestId() { return testId; }
    public void setTestId(Long testId) { this.testId = testId; }

    public Map<Long, List<Integer>> getAnswers() { return answers; }
    public void setAnswers(Map<Long, List<Integer>> answers) { this.answers = answers; }
}
