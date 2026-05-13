package com.testing_system.repository;

import com.testing_system.model.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerOptionRepository
        extends JpaRepository<AnswerOption, Long> {

}