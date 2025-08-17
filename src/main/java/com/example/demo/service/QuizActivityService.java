package com.example.demo.service;

import com.example.demo.domain.QuizActivity;
import com.example.demo.repository.QuizActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizActivityService {

    private final QuizActivityRepository repository;

    public List<QuizActivityResponse> getActivityForLastYear(Long memberId) {
        LocalDate today = LocalDate.now();
        LocalDate oneYearAgo = today.minusYears(1);
        List<QuizActivity> activities = repository.findByMemberIdAndDateBetween(memberId, oneYearAgo, today);

        // Map to date -> solved
        Map<LocalDate, Boolean> activityMap = activities.stream()
                .collect(Collectors.toMap(QuizActivity::getDate, QuizActivity::isSolved));

        // Fill in missing dates with solved=false
        List<QuizActivityResponse> result = new ArrayList<>();
        for (LocalDate date = oneYearAgo; !date.isAfter(today); date = date.plusDays(1)) {
            boolean solved = activityMap.getOrDefault(date, false);
            result.add(new QuizActivityResponse(date, solved));
        }

        return result;
    }
}

