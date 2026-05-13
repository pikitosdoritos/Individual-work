package com.testing_system.service;

import com.testing_system.exception.ResourceNotFoundException;
import com.testing_system.model.AnswerOption;
import com.testing_system.repository.AnswerOptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerOptionService {

    private final AnswerOptionRepository repository;

    public AnswerOptionService(
            AnswerOptionRepository repository) {

        this.repository = repository;
    }

    public List<AnswerOption> getAllAnswerOptions() {
        return repository.findAll();
    }

    public AnswerOption getAnswerOptionById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Answer option not found"));
    }

    public AnswerOption saveAnswerOption(
            AnswerOption answerOption) {

        return repository.save(answerOption);
    }

    public void deleteAnswerOption(Long id) {
        repository.deleteById(id);
    }
}