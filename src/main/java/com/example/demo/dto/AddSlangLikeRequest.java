package com.example.demo.dto;

import com.example.demo.domain.Slang;
import com.example.demo.domain.SlangLike;
import com.example.demo.domain.User;
import lombok.Builder;

@Builder
public class AddSlangLikeRequest {

    private Slang slang;
    private User user;

    public static AddSlangLikeRequest of(Slang slang, User user) {
        return AddSlangLikeRequest.builder()
                .slang(slang)
                .user(user)
                .build();
    }

    public SlangLike toEntity() {
        return SlangLike.builder()
                .slang(slang)
                .user(user)
                .build();
    }
}
