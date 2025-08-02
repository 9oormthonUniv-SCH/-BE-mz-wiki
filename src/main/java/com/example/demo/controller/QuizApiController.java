package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.QuizAnswerRequest;
import com.example.demo.dto.QuizDTO;
import com.example.demo.dto.QuizHistoryDTO;
import com.example.demo.repository.SlangRepository;
import com.example.demo.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/history")
    @Operation(summary = "최근 퀴즈 기록 조회", description = "최근 n일간 사용자가 푼 퀴즈 기록을 가져옵니다.")
    public ResponseEntity<List<QuizHistoryDTO>> getQuizHistory(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "7") int days) {

        List<QuizHistoryDTO> history = quizService.getRecentHistory(user, days);
        return ResponseEntity.ok(history);
    }
}
