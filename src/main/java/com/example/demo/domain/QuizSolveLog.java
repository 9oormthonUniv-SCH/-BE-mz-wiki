package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class QuizSolveLog {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private LocalDate solvedDate;  // 푼 날짜
    private boolean correct;       // 정답 여부
}
