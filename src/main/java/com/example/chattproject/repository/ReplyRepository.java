package com.example.chattproject.repository;

import com.example.chattproject.domain.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//엔티티 클래스이름 , ID 필드 타입
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    /**
     * 특정한 게시글의 댓글들을 페이징 커리를 할 수 있도록 pageable 기능을 query를 이용ㅇ해서 작성
     * @param bno
     * @param pageable
     * @return
     */
    @Query("select r from Reply r where r.board.bno = :bno")
    Page<Reply> listOfBoard(Long bno, Pageable pageable);
}
