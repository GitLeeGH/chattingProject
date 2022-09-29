package com.example.chattproject.repository;

import com.example.chattproject.domain.entity.Board;
import com.example.chattproject.domain.entity.BoardImage;
import com.example.chattproject.dto.BoardDTO;
import com.example.chattproject.dto.BoardListAllDTO;
import com.example.chattproject.dto.PageRequestDTO;
import com.example.chattproject.dto.PageResponseDTO;
import com.example.chattproject.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardService boardService;


    @Test
    public void testInsert() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Board board = Board.builder()
                    .title("title...." + i)
                    .content("content...." +i)
                    .writer("user " + (i % 10))
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO : " + result.getBno());
        });
    }

    @Test
    public void testSelect() {
        Long bno =100L;

        Optional<Board> result = boardRepository.findById(bno);

        Optional<Board> board = result;

        log.info(board);
    }

    @Test
    public void testUpdate() {

        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow(IllegalArgumentException::new);

        board.change("update...title 100", "update content 100");

        boardRepository.save(board);
    }

    @Test
    public void testDelete() {

        Long bno = 1L;

        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging() {

        // 1 page order by bno desc

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count : " + result.getTotalElements());
        log.info("total pages : " + result.getTotalPages());
        log.info("page number" + result.getNumber());
        log.info("page size : " + result.getSize());

        List<Board> todoList = result.getContent();

        todoList.forEach(board -> log.info(board));

    }

    @Test
    public void  testSearch1() {

        // 2 page order by bno desc
        Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());


        boardRepository.search1(pageable);
    }

    @Test
    public void testSearchAll() {

        String[] types = {"t", "c", "w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        // total pages
        log.info(result.getTotalPages());

        // page size
        log.info(result.getSize());

        // pageNumber
        log.info(result.getNumber());

        // prev next
        log.info(result.hasPrevious() + " : " + result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void testModify() {

        // 변경에 필요한 데이터만
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("Updated....101")
                .content("Updated content 101..........")
                .build();

        boardService.modify(boardDTO);

    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);
    }

    @Test
    public void testInsertWithImages() {

        Board board = Board.builder()
                .title("Image Test")
                .content("첨부파일 테스트")
                .writer("tester")
                .build();

        for(int i=0; i < 3; i++) {

            board.addImage(UUID.randomUUID().toString(), "file" + i + ".jpg");
        } // end for

        boardRepository.save(board);
    }

    @Test
    public void testReadWithImages() {

        // 반드시 존재하는 bno 로 확인
        Optional<Board> result  = boardRepository.findByIdWithImages(1L);

        Board board = result.orElseThrow();

        log.info(board);
        log.info("----------------------------------");
        for(BoardImage boardImage : board.getImageSet()) {
            log.info(boardImage);
        }
    }

    @Transactional
    @Commit
    @Test
    public void testModifyImages() {

        Optional<Board> result = boardRepository.findByIdWithImages(1L);

        Board board = result.orElseThrow();

        // 기존의 첨부 파일들은 삭제
        board.clearImages();

        // 새로운 첨부파일들
        for(int i=0; i<2; i++) {

            board.addImage(UUID.randomUUID().toString(), "updatefile" + i + ".jpg");
        }

        boardRepository.save(board);
    }

    @Test
    @Transactional
    @Commit
    public void testRemoveAll() {

        // Reply 엔티티들을 삭제한 후 Board 를 삭제

        Long bno = 1L;

        replyRepository.deleteByBoard_Bno(bno);

        boardRepository.deleteById(bno);
    }

    @Test
    public void testInsertAll() {

        for(int i = 1; i <= 100; i++){

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("writer.." + i)
                    .build();

            for(int j = 0; j < 3; j++) {

                if(i % 5 == 0){
                    continue;
                }
                board.addImage(UUID.randomUUID().toString(), i + "file" + j + ".jpg");
            }

            boardRepository.save(board);
        }   // end for
    }

    @Transactional
    @Test
    public void testSearchImageReplyCount() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        //boardRepository.searchWithAll(null, null, pageable);

        Page<BoardListAllDTO> result = boardRepository.searchWithAll(null,null,pageable);

        log.info("--------------------------------------------------------------------------------------");
        log.info(result.getTotalElements());

        result.getContent().forEach(boardListAllDTO -> log.info(boardListAllDTO));
    }
}
