package com.example.demo.controller;

import com.example.demo.domain.Slang;
import com.example.demo.service.SlangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slangs")
@RequiredArgsConstructor
@Tag(name = "신조어 API", description = "신조어 검색 기능을 제공합니다")
public class SlangController {

    private final SlangService slangService;

    @GetMapping("/search")
    @Operation(summary = "신조어 검색", description = "키워드를 포함한 신조어 목록을 반환합니다")
    public List<Slang> searchSlang(@RequestParam String keyword) {
        return slangService.searchSlangs(keyword);
    }
}
