package com.example.demo.repository;

import com.example.demo.domain.Slang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlangRepository extends JpaRepository<Slang, Long> {

    // 부분 검색이 가능하도록 LIKE 쿼리로 변경
    @Query("SELECT s FROM Slang s WHERE s.term LIKE %:keyword%")
    List<Slang> findByTermContaining(@Param("keyword") String keyword);
}

