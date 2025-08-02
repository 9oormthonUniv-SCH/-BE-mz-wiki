package com.example.demo.repository;

import com.example.demo.domain.Quiz;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByUserAndQuizDate(User user, LocalDate quizDate);

    List<Quiz> findByUserAndQuizDateAfterOrderByQuizDateDesc(User user, LocalDate date);

}



