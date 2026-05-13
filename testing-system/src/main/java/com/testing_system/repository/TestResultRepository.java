package com.testing_system.repository;

import com.testing_system.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository
        extends JpaRepository<TestResult, Long> {

}