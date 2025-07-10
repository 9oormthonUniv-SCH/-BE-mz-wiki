package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserApiControllerTest {

    private MockMvc mockMvc;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class); // Mockito 직접 생성
        UserApiController userApiController = new UserApiController(userService); // 수동 주입
        mockMvc = MockMvcBuilders.standaloneSetup(userApiController).build(); // Security 비적용
    }

    @Test
    @DisplayName("중복 이메일이면 duplicate=true 반환")
    void checkEmail_duplicate_true() throws Exception {
        // given
        String email = "test@example.com";
        when(userService.isEmailDuplicate(email)).thenReturn(true);

        // when & then
        mockMvc.perform(get("/user/check-email")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.duplicate").value(true));
    }

    @Test
    @DisplayName("중복 이메일이 아니면 duplicate=false 반환")
    void checkEmail_duplicate_false() throws Exception {
        // given
        String email = "unique@example.com";
        when(userService.isEmailDuplicate(email)).thenReturn(false);

        // when & then
        mockMvc.perform(get("/user/check-email")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.duplicate").value(false));
    }
}
