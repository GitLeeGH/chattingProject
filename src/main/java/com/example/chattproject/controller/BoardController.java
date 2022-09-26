package com.example.chattproject.controller;

import com.example.chattproject.dto.BoardDTO;
import com.example.chattproject.dto.PageRequestDTO;
import com.example.chattproject.dto.PageResponseDTO;
import com.example.chattproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("board/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO",responseDTO);

    }
}
