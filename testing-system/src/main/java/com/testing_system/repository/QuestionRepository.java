package com.testing_system.repository;

import com.testing_system.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository
        extends JpaRepository<Question, Long> {

}