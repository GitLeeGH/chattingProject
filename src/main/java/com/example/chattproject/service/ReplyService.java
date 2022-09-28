package com.example.chattproject.service;

import com.example.chattproject.dto.PageRequestDTO;
import com.example.chattproject.dto.PageResponseDTO;
import com.example.chattproject.dto.ReplyDTO;

public interface ReplyService {

    // 댓글 등록
    Long register(ReplyDTO replyDTO);

    // 댓글 조회
    ReplyDTO read(Long rno);

    // 댓글 수정
    void modify(ReplyDTO replyDTO);

    // 댓글 삭제
    void remove(Long rno);

    // 댓글 페이징 처리
    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);
}
