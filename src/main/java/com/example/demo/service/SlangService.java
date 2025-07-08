package com.example.demo.service;

import com.example.demo.domain.Slang;
import com.example.demo.dto.AddSlangRequest;
import com.example.demo.repository.SlangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
