package com.example.chattproject.repository;

import com.example.chattproject.domain.entity.Board;

import com.example.chattproject.repository.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> , BoardSearch {

    @Query(value = "select now()", nativeQuery = true)
    String getTime();

}
