package com.example.demo.controller;

import com.example.demo.domain.Slang;
import com.example.demo.dto.AddSlangRequest;
import com.example.demo.dto.UpdateSlangRequest;
import com.example.demo.service.SlangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slangs")
@RequiredArgsConstructor
@Tag(name = "신조어 API", description = "신조어 CRUD 기능을 제공합니다")
public class SlangApiController {

    private final SlangService slangService;

    // 신조어 검색
    @GetMapping
    @Operation(summary = "신조어 검색", description = "키워드를 포함한 신조어 목록을 반환합니다")
    public List<Slang> searchSlang(@RequestParam String keyword) {
        return slangService.searchSlangs(keyword);
    }

    // 신조어 추가
    @PostMapping
    @Operation(
            summary = "신조어 등록",
            description = "신조어와 그 뜻, 예시를 등록합니다. JSON 형태로 term, meaning, example을 입력하세요.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "신조어 등록 성공"),
                    @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    public ResponseEntity<Slang> addSlang(@RequestBody AddSlangRequest request) {
        Slang savedSlang = slangService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSlang);
    }

    // 신조어 수정
    @PutMapping("/{slang_id}")
    @Operation(
            summary = "신조어 수정",
            description = "신조어 ID에 해당하는 신조어의 뜻과 예시를 수정합니다. JSON 형태로 meaning, example을 입력하세요.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "신조어 수정 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 신조어를 찾을 수 없음"),
                    @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    public ResponseEntity<Slang> updateSlang(@PathVariable long slang_id, @RequestBody UpdateSlangRequest request) {
        Slang updateSlang = slangService.update(slang_id, request);

        return ResponseEntity.ok()
                .body(updateSlang);
    }
}
