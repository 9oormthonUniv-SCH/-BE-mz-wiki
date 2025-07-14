package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlangLike {

    // 키 값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 좋아요 누른 유저
    @ManyToOne
    private User user;

    // 좋아요 누른 신조어
    @ManyToOne
    private Slang slang;
}
