package com.example.chattproject.service;

import com.example.chattproject.domain.entity.Board;
import com.example.chattproject.dto.BoardDTO;
import com.example.chattproject.dto.PageRequestDTO;
import com.example.chattproject.dto.PageResponseDTO;
import com.example.chattproject.repository.BoardRepository;
import javafx.beans.property.StringProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO boardDTO) {

        Board board = modelMapper.map(boardDTO, Board.class);

        Long bno = boardRepository.save(board).getBno();

        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow(IllegalArgumentException::new);

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        Board board = result.orElseThrow(() -> new IllegalArgumentException("no such data"));

        board.change(boardDTO.getTitle(), boardDTO.getContent());

        boardRepository.save(board);

    }

    @Override
    public void remove(Long bno) {

        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board,BoardDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }


}
