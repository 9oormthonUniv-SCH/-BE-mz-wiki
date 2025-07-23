package com.example.demo.repository;

import com.example.demo.domain.Slang;
import com.example.demo.domain.SlangLike;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlangLikeRepository extends JpaRepository<SlangLike, Long> {
    Optional<SlangLike> findBySlangIdAndUserUsername(Long slangId, String email);
    boolean existsBySlangIdAndUserUsername(Long slangId, String email);
}