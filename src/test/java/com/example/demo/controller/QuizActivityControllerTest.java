package com.example.demo.test;

import com.example.demo.controller.QuizActivityController;
import com.example.demo.domain.User;
import com.example.demo.security.MemberDetails;
import com.example.demo.service.QuizActivityResponse;
import com.example.demo.service.QuizActivityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;

@WebMvcTest(QuizActivityController.class)
public class QuizActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizActivityService quizActivityService;

    @Test
    @DisplayName("최근 1년 퀴즈 활동 API 테스트")
    void testGetMyQuizActivity() throws Exception {
        // 가짜 유저 생성
        User mockUser = User.builder()
                .email("test@example.com")
                .password("test1234")
                .build();

        Long mockMemberId = 1L;
        ReflectionTestUtils.setField(mockUser, "id", mockMemberId);

        // 인증된 사용자 정보 (MemberDetails)
        MemberDetails mockMemberDetails = new MemberDetails(mockUser);

        // 반환될 Mock 응답 데이터
        List<QuizActivityResponse> mockResponse = List.of(
                new QuizActivityResponse(LocalDate.parse("2025-08-13"), true),
                new QuizActivityResponse(LocalDate.parse("2025-08-12"), false)
        );

        // Service 계층 Mock 동작 정의
        Mockito.when(quizActivityService.getActivityForLastYear(mockMemberId))
                .thenReturn(mockResponse);

        // 실제 테스트 수행
        mockMvc.perform(get("/api/quiz/activity")
                        .with(authentication(new TestingAuthenticationToken(
                                mockMemberDetails,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_USER")) // 권한 부여 중요!!
                        )))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].date", is("2025-08-13")))
                .andExpect(jsonPath("$[0].solved", is(true)))
                .andExpect(jsonPath("$[1].date", is("2025-08-12")))
                .andExpect(jsonPath("$[1].solved", is(false)));
    }
}
