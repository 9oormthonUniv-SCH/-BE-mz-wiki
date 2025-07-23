package com.example.demo.service;

import com.example.demo.domain.SlangLike;
import com.example.demo.domain.Slang;
import com.example.demo.domain.User;
import com.example.demo.dto.AddSlangLikeRequest;
import com.example.demo.dto.AddSlangRequest;
import com.example.demo.dto.UpdateSlangRequest;
import com.example.demo.repository.SlangLikeRepository;
import com.example.demo.repository.SlangRepository;
import com.example.demo.repository.UserRepository;
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
    private final UserRepository userRepository;

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

    // 신조어 삭제
    public void delete(Long slang_id) {
        // 예외처리....:3
        Slang slang = slangRepository.findById(slang_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신조어입니다."));
        slangRepository.deleteById(slang_id);
    }

    // 좋아요 추가
    public void addLike(Long slangId, String email) {
        if (slangLikeRepository.existsBySlangIdAndUserEmail(slangId, email)) {
            throw new IllegalStateException("이미 좋아요를 누른 상태입니다.");
        }

        Slang slang = slangRepository.findById(slangId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신조어입니다."));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        AddSlangLikeRequest request = AddSlangLikeRequest.of(slang, user);
        slangLikeRepository.save(request.toEntity());
    }


    // 좋아요 삭제
    public void deleteLike(Long slangId, String email) {
        SlangLike like = slangLikeRepository.findBySlangIdAndUserEmail(slangId, email)
                .orElseThrow(() -> new IllegalStateException("좋아요를 누르지 않았습니다."));

        slangLikeRepository.delete(like);
    }

    // 좋아요 목록 조회
    public List<Slang> getLikedSlangs(String email) {
        return slangLikeRepository.findLikedSlangsByUserEmail(email);
    }

}
