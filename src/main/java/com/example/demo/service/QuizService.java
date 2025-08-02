package com.example.demo.service;

import com.example.demo.domain.Quiz;
import com.example.demo.domain.Slang;
import com.example.demo.domain.User;
import com.example.demo.dto.QuizAnswerRequest;
import com.example.demo.dto.QuizDTO;
import com.example.demo.dto.QuizHistoryDTO;
import com.example.demo.repository.QuizRepository;
import com.example.demo.repository.SlangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final SlangRepository slangRepository;
    private final QuizRepository quizRepository;

    public QuizDTO generateDailyQuiz(User user) {
        LocalDate today = LocalDate.now();

        // 이미 푼 퀴즈가 있으면 해당 퀴즈 반환
        Optional<Quiz> existing = quizRepository.findByUserAndQuizDate(user, today);
        if (existing.isPresent()) {
            return new QuizDTO(existing.get().getQuestion());
        }

        // 새로운 퀴즈 생성
        List<Slang> allSlangs = slangRepository.findAll();
        Slang random = allSlangs.get(new Random().nextInt(allSlangs.size()));

        Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setQuestion(random.getTerm());
        quiz.setAnswer(random.getMeaning());
        quiz.setQuizDate(today);
        quiz.setCorrect(false); // 아직 안 풀었으므로

        quizRepository.save(quiz);
        return new QuizDTO(quiz.getQuestion());
    }

    public boolean submitAnswer(User user, QuizAnswerRequest request) {
        Quiz quiz = quizRepository.findByUserAndQuizDate(user, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("오늘 퀴즈가 없습니다."));

        boolean correct = quiz.getAnswer().equalsIgnoreCase(request.getAnswer());
        quiz.setCorrect(correct);
        quizRepository.save(quiz);

        return correct;
    }

    public List<QuizHistoryDTO> getRecentHistory(User user, int days) {
        LocalDate fromDate = LocalDate.now().minusDays(days);
        List<Quiz> quizzes = quizRepository.findByUserAndQuizDateAfterOrderByQuizDateDesc(user, fromDate);

        return quizzes.stream()
                .map(q -> new QuizHistoryDTO(q.getQuizDate(), q.getQuestion(), q.isCorrect()))
                .collect(Collectors.toList());
    }
}
