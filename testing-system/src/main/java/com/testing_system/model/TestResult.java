package com.testing_system.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_results")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;

    public TestResult() {
    }

    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public Student getStudent() {
        return student;
    }

    public TestEntity getTest() {
        return test;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }
}