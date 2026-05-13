package com.testing_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Question text cannot be empty")
    private String text;

    private int points;

    @ManyToOne
    @JoinColumn(name = "test_id")
    @JsonBackReference
    private TestEntity test;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AnswerOption> answerOptions = new ArrayList<>();

    public Question() {
    }

    public Question(
            String text,
            int points) {
        this.text = text;
        this.points = points;
    }

    public void addAnswerOption(
            AnswerOption option) {

        answerOptions.add(option);

        option.setQuestion(this);
    }

    public void removeAnswerOption(
            AnswerOption option) {

        answerOptions.remove(option);

        option.setQuestion(null);
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPoints() {
        return points;
    }

    public TestEntity getTest() {
        return test;
    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }

    public void setAnswerOptions(
            List<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }
}