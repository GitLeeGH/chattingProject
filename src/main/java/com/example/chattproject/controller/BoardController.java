package com.example.chattproject.controller;

import com.example.chattproject.domain.entity.Board;
import com.example.chattproject.dto.*;
import com.example.chattproject.repository.BoardRepository;
import com.example.chattproject.service.BoardService;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    @Value("${com.example.upload.path}")    //import 시에 springframework으로 시작하는 Value
    private String uploadPath;

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        //PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        //PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);
        PageResponseDTO<BoardListAllDTO> responseDTO = boardService.listWithAll(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO",responseDTO);

    }

    /**
     *  게시글 등록 처리 get 으로 화면 보고 post 방식으로 처리
     */
    @GetMapping("/register")
    public void registerGET(){


    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        log.info("board POSt register.............");

        if(bindingResult.hasErrors()) {
            log.info("has errors.........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/board/register";
        }

        log.info("boardDTO : " + boardDTO);

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }


    /**
     * 게시글 조회 / 수정
     */
    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        model.addAttribute("dto",boardDTO);
    }

    /**
     *  게시글 수정 처리
     */
    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO, @Valid BoardDTO boardDTO, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){

        log.info("board modify post ..............." + boardDTO);

        if(bindingResult.hasErrors()) {
            log.info("has errors ...........");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno",boardDTO.getBno());

            return "redirect:/board/modify?"+link;
        }

        boardService.modify(boardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
    }

    /**
     *  게시글 삭제 처리
     */
    @PostMapping("/remove")
    public String remove(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {

        Long bno = boardDTO.getBno();

        log.info("remove post .............." + bno);

        boardService.remove(bno);

        // 게시물이 데이터베이스상에서 삭제되었다면 첨부파일 삭제
        log.info(boardDTO.getFileNames());
        List<String> fileNames = boardDTO.getFileNames();
        if(fileNames != null && fileNames.size() >0){
            removeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result" , "removed");

        return "redirect:/board/list";
    }

    private void removeFiles(List<String> files) {

        for(String fileName:files){

            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

            String resourceName = resource.getFilename();


            try {

                String contentType = Files.probeContentType(resource.getFile().toPath());

                resource.getFile().delete();

                // 썸네일이 존재한다면
                if(contentType.startsWith("image")) {
                    File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);

                    thumbnailFile.delete();
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }


        }// end for
    }


}
