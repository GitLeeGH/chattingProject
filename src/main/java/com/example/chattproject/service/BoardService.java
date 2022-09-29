package com.example.chattproject.service;

import com.example.chattproject.domain.entity.Board;
import com.example.chattproject.dto.*;

import java.util.List;
import java.util.stream.Collectors;

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

    // 댓글의 숫자까지 처리
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

    // 게시글의이미지와 댓글의 숫자까지 처리
    PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);


    /**
     * DTO를 엔티티로 변환하기
     *  기존의 ModelMapper 는 단순한 구조의 객체를 다른 타입의 객체로 만드는데는 편리하기만
     *  다양한 처리가 피룡한 겨웅에는 오히려 더 복잡하기 때문에 DTO 객체를 엔티티 객체로 변환하는 메소드 작성
     * @param boardDTO
     * @return
     */
    default Board dtoToEntity(BoardDTO boardDTO) {

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();

        if(boardDTO.getFileNames() != null){
            boardDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                board.addImage(arr[0], arr[1]);
            });
        }

        return board;
    }

    /**
     * Board 엔티티 객체를 BoardDTO 타입으로 변환처리
     * @param board
     * @return
     */
    default BoardDTO entityTODTO(Board board){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        List<String> fileName = board.getImageSet().stream().sorted().map(boardImage -> boardImage.getUuid()
                                    + "_" + boardImage.getFileName()).collect(Collectors.toList());

        boardDTO.setFileNames(fileName);

        return boardDTO;
    }


}
