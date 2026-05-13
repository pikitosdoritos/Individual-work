package com.testing_system.repository;

import com.testing_system.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository
                extends JpaRepository<Student, Long> {

}