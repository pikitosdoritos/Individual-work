package com.testing_system.config;

import com.testing_system.model.Question;
import com.testing_system.model.QuestionType;
import com.testing_system.model.Role;
import com.testing_system.model.Test;
import com.testing_system.model.User;
import com.testing_system.repository.TestRepository;
import com.testing_system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TestRepository testRepository;
    private final UserRepository userRepository;

    public DataInitializer(TestRepository testRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User student = new User();
            student.setUsername("student");
            student.setPassword("student123");
            student.setRole(Role.STUDENT);
            userRepository.save(student);

            User tutor = new User();
            tutor.setUsername("tutor");
            tutor.setPassword("tutor123");
            tutor.setRole(Role.TUTOR);
            userRepository.save(tutor);
        }

        if (testRepository.count() > 0) return;
        
        Test test1 = new Test();
        test1.setTitle("Java Programming Basics");
        test1.setSubject("Java");
        test1.setQuestions(List.of(
                buildQuestion(test1, "Which keyword creates a new object in Java?",
                        QuestionType.SINGLE, List.of("create", "new", "object", "make"), List.of(1)),
                buildQuestion(test1, "Which are valid Java access modifiers?",
                        QuestionType.MULTIPLE, List.of("public", "private", "internal", "protected"), List.of(0, 1, 3)),
                buildQuestion(test1, "What is the default value of an int field?",
                        QuestionType.SINGLE, List.of("null", "1", "0", "-1"), List.of(2)),
                buildQuestion(test1, "Which interfaces does ArrayList implement?",
                        QuestionType.MULTIPLE, List.of("List", "Set", "Serializable", "Queue"), List.of(0, 2))
        ));
        testRepository.save(test1);

        Test test2 = new Test();
        test2.setTitle("Spring Boot Fundamentals");
        test2.setSubject("Spring");
        test2.setQuestions(List.of(
                buildQuestion(test2, "Which annotation marks a Spring-managed component?",
                        QuestionType.SINGLE, List.of("@Bean", "@Component", "@Entity", "@Controller"), List.of(1)),
                buildQuestion(test2, "Which annotations are used for dependency injection?",
                        QuestionType.MULTIPLE, List.of("@Autowired", "@Inject", "@Value", "@Override"), List.of(0, 1)),
                buildQuestion(test2, "What annotation maps a method to HTTP GET?",
                        QuestionType.SINGLE, List.of("@PostMapping", "@GetMapping", "@RequestBody", "@PathVariable"), List.of(1)),
                buildQuestion(test2, "Which annotations are used in Spring Data JPA?",
                        QuestionType.MULTIPLE, List.of("@Entity", "@Table", "@Id", "@Service"), List.of(0, 1, 2))
        ));
        testRepository.save(test2);
    }

    private Question buildQuestion(Test test, String text, QuestionType type,
                                   List<String> options, List<Integer> correctAnswers) {
        Question question = new Question();
        question.setText(text);
        question.setType(type);
        question.setOptions(options);
        question.setCorrectAnswers(correctAnswers);
        question.setTest(test);
        return question;
    }
}
