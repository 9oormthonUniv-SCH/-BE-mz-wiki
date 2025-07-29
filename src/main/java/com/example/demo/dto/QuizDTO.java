package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizDTO {
    private String question;

    public QuizDTO(String question) {
        this.question = question;
    }
}

