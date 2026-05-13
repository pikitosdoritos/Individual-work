package com.testing_system.service;

import com.testing_system.exception.ResourceNotFoundException;
import com.testing_system.model.Student;
import com.testing_system.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found"));
    }

    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    public Student updateStudent(
            Long id,
            Student updatedStudent) {

        Student student = getStudentById(id);

        student.setName(updatedStudent.getName());
        student.setAge(updatedStudent.getAge());

        return repository.save(student);
    }

    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }
}