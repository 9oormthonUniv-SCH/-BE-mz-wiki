package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class QuizHistoryDTO {
    private LocalDate date;
    private String question;
    private boolean correct;
}
