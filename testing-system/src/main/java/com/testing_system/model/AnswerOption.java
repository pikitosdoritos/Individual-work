package com.testing_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "answer_options")
public class AnswerOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Answer text cannot be empty")
    private String text;

    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;

    public AnswerOption() {
    }

    public AnswerOption(
            String text,
            boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public void setQuestion(
            Question question) {
        this.question = question;
    }
}