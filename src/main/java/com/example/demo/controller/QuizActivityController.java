package com.example.demo.controller;

import com.example.demo.security.MemberDetails;
import com.example.demo.service.QuizActivityResponse;
import com.example.demo.service.QuizActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizActivityController {

    private final QuizActivityService quizActivityService;

    @GetMapping("/activity")
    public List<QuizActivityResponse> getMyQuizActivity(@AuthenticationPrincipal MemberDetails memberDetails) {
        Long memberId = memberDetails.getId();
        return quizActivityService.getActivityForLastYear(memberId);
    }
}

