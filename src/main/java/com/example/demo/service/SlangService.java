package com.example.demo.service;

import com.example.demo.domain.SlangLike;
import com.example.demo.domain.Slang;
import com.example.demo.domain.User;
import com.example.demo.dto.AddSlangRequest;
import com.example.demo.dto.UpdateSlangRequest;
import com.example.demo.repository.SlangLikeRepository;
import com.example.demo.repository.SlangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SlangService {

    private final SlangRepository slangRepository;
    private final SlangLikeRepository slangLikeRepository;
    private final UserService userService;

    // 신조어 검색
    public List<Slang> searchSlangs(String keyword) {
        return slangRepository.findByTermContaining(keyword);
    }

    // 신조어 추가
    public Slang save(AddSlangRequest request) {
        return slangRepository.save(request.toEntity());
    }

    // 신조어 수정
    @Transactional
    public Slang update(long id, UpdateSlangRequest request) {
        Slang slang = slangRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Slang not found: " + id));

        slang.update(request.getTerm(), request.getMeaning(), request.getExample());

        return slang;
    }

    // 좋아요
    public boolean toggleLike(Long slangId, String email) {
        Slang slang = slangRepository.findById(slangId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신조어가 없습니다."));
        User user = userService.findByEmail(email);

        Optional<SlangLike> existingLike = slangLikeRepository.findByUserAndSlang(user, slang);

        if (existingLike.isPresent()) {
            slangLikeRepository.delete(existingLike.get());
            slang.setLikeCount(slang.getLikeCount() - 1);
            slangRepository.save(slang);
            return false;  // 좋아요 취소됨
        } else {
            SlangLike newLike = SlangLike.builder()
                    .user(user)
                    .slang(slang)
                    .build();
            slangLikeRepository.save(newLike);
            slang.setLikeCount(slang.getLikeCount() + 1);
            slangRepository.save(slang);
            return true;  // 좋아요 등록됨
        }
    }


}
