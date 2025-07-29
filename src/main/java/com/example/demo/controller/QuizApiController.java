package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.QuizAnswerRequest;
import com.example.demo.dto.QuizDTO;
import com.example.demo.repository.SlangRepository;
import com.example.demo.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizApiController {
    private final SlangRepository slangRepository;
    private final QuizService quizService;

    @GetMapping("/daily")
    public ResponseEntity<QuizDTO> getDailyQuiz(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(quizService.generateDailyQuiz(user));
    }

    @PostMapping("/answer")
    public ResponseEntity<String> checkAnswer(@RequestBody QuizAnswerRequest request,
                                              @AuthenticationPrincipal User user) {
        boolean correct = quizService.submitAnswer(user, request);
        return ResponseEntity.ok(correct ? "정답입니다!" : "오답입니다.");
    }
}
