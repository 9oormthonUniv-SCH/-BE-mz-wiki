package com.example.demo.service;

import com.example.demo.domain.Slang;
import com.example.demo.repository.SlangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SlangService {

    private final SlangRepository slangRepository;

    public List<Slang> searchSlangs(String keyword) {
        return slangRepository.findByTermContaining(keyword);
    }
}
