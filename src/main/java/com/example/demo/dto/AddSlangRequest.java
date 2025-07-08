package com.example.demo.dto;

import com.example.demo.domain.Slang;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddSlangRequest {

    private String term;
    private String meaning;
    private String example;

    public Slang toEntity() {
        return Slang.builder()
                .term(term)
                .meaning(meaning)
                .example(example)
                .build();
    }
}
