package com.example.chattproject.repository.search;

import com.example.chattproject.domain.entity.Board;
import com.example.chattproject.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {

    Page<Board> search1(Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);


}
