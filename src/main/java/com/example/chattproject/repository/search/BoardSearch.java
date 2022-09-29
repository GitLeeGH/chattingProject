package com.example.chattproject.repository.search;

import com.example.chattproject.domain.entity.Board;
import com.example.chattproject.dto.BoardDTO;
import com.example.chattproject.dto.BoardListAllDTO;
import com.example.chattproject.dto.BoardListReplyCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {

    Page<Board> search1(Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    // 댓글 댓수
    Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

    Page<BoardListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable);


}
