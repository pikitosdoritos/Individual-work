package com.testing_system.repository;

import com.testing_system.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository
        extends JpaRepository<TestEntity, Long> {

}