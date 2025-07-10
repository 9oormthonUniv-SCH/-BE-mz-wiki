package com.example.demo.service;

import com.example.demo.domain.Slang;
import com.example.demo.dto.AddSlangRequest;
import com.example.demo.dto.UpdateSlangRequest;
import com.example.demo.repository.SlangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SlangService {

    private final SlangRepository slangRepository;

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
}
