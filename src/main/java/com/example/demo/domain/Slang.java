package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slang {

    // 키 값
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 신조어
    @Column(nullable = false, length = 100)
    private String term;

    // 뜻
    @Column(nullable = false, columnDefinition = "TEXT")
    private String meaning;

    // 사용 예시
    @Column(columnDefinition = "TEXT")
    private String example;
}
