package com.example.demo.repository;

import com.example.demo.domain.Slang;
import com.example.demo.domain.SlangLike;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SlangLikeRepository extends JpaRepository<SlangLike, Long> {
    // 좋아요 추가
    Optional<SlangLike> findBySlangIdAndUserEmail(Long slangId, String email);
    boolean existsBySlangIdAndUserEmail(Long slangId, String email);

    // 좋아요 목록 조회
    List<SlangLike> findAllByUserEmail(String email);
    @Query("SELECT sl.slang FROM SlangLike sl WHERE sl.user.email = :email")
    List<Slang> findLikedSlangsByUserEmail(@Param("email") String email);
}