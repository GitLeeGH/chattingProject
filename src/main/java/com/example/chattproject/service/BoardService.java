package com.example.chattproject.service;

import com.example.chattproject.dto.BoardDTO;
import com.example.chattproject.dto.PageRequestDTO;
import com.example.chattproject.dto.PageResponseDTO;

public interface BoardService {

    // 게시판 등록
    Long register(BoardDTO boardDTO);

    // 게시판 조회
    BoardDTO readOne(Long bno);

    // 게시글 수정
    void modify(BoardDTO boardDTO);

    // 게시글 삭제
    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);



}
