package com.example.demo.service;

import com.example.demo.domain.Slang;
import com.example.demo.repository.SlangRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SlangServiceTest {
    @Autowired
    private SlangService slangService;

    @Autowired
    private SlangRepository slangRepository;

    private Slang savedSlang;

    @BeforeEach
    void setUp() {
        slangRepository.deleteAll(); // 매 테스트마다 데이터 클리어

        // 테스트용 신조어 저장
        Slang slang = Slang.builder()
                .term("어쩔티비")
                .meaning("어쩌라고")
                .example("어쩔티비어쩔냉장고어쩔삼성OLEDUHD스마트티비")
                .build();

        savedSlang = slangRepository.save(slang);
    }

    @Test
    void searchSlangs() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @DisplayName("신조어 삭제 테스트")
    @Test
    void delete() {
        Long slang_id = savedSlang.getId();
        slangService.delete(slang_id);

        Optional<Slang> deleted = slangRepository.findById(slang_id);
        assertThat(deleted).isEmpty();
    }

    @Test
    void toggleLike() {
    }
}