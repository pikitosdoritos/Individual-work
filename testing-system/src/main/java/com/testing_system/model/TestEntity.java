package com.testing_system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tests")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String subject;

    private String description;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Question> questions = new ArrayList<>();

    public TestEntity() {
    }

    public TestEntity(
            String title,
            String subject,
            String description) {
        this.title = title;
        this.subject = subject;
        this.description = description;
    }

    public void addQuestion(Question question) {

        questions.add(question);

        question.setTest(this);
    }

    public void removeQuestion(Question question) {

        questions.remove(question);

        question.setTest(null);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuestions(
            List<Question> questions) {
        this.questions = questions;
    }
}