package com.testing_system.dto;

import com.testing_system.model.QuestionType;

import java.util.List;

public class QuestionResponse {

    private Long id;
    private String text;
    private QuestionType type;
    private List<String> options;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public QuestionType getType() { return type; }
    public void setType(QuestionType type) { this.type = type; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
}
