package com.example.chattproject.controller;

import com.example.chattproject.dao.ChatRepository;
import com.example.chattproject.domain.entity.ChatRoomEntity;
import com.example.chattproject.dto.ChatRoom;
import com.example.chattproject.dto.ChatRoomDTO;
import com.example.chattproject.repository.ChatRoomEntityRepository;
import com.example.chattproject.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@Slf4j
public class ChatRoomController {

    // ChatRepository Bean 가져오기
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatRoomEntityRepository chatRoomEntityRepository;

    @Autowired
    private ChatService chatService;

    // 채팅 리스트 화면
    // /chat 로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return

    // 스프링 시큐리티의 로그인 유저 정보는 Security 세션의 PrincipalDetails 안에 담긴다
    // 정확히는 PrincipalDetails 안에 ChatUser 객체가 담기고, 이것을 가져오면 된다.
    @GetMapping("/chat")
    public String goChatRoom(Model model){

        model.addAttribute("list",chatRepository.findAllRoom());
        log.info("SHOW ALL ChatList {}", chatRepository.findAllRoom());
        return "roomlist";
    }


    // 채팅방 생성
    // 채팅방 생성 후 다시 /chat 로 return
    @PostMapping("/chat/creatroom")
    public String creatRoom(@RequestParam("roomName") String name, @RequestParam("roomPwd")String roomPwd, @RequestParam("secretChk")String secretChk,
                            @RequestParam(value = "maxUserCnt", defaultValue = "100")String maxUserCnt, RedirectAttributes rttr , ChatRoomDTO dto){

        ChatRoom room = chatRepository.creatChatRoom(name, roomPwd, Boolean.parseBoolean(secretChk), Integer.parseInt(maxUserCnt));

//       dto 를 entity 로 변환
//        ChatRoomEntity chatRoomEntity1 = dto.toEntity();

//        ChatRoomEntity chatRoomEntity =  new ChatRoomEntity();
//        chatRoomEntity.setRoomId(UUID.randomUUID().toString());
//        chatRoomEntity.setRoomName(name);
//        chatRoomEntity.setRoomPwd(roomPwd);
//        chatRoomEntity.setSecretChk(Boolean.parseBoolean(secretChk));
//        chatRoomEntity.setMaxUserCnt(Integer.parseInt(maxUserCnt));
//        System.out.println("chatRoomEntity1 : " + chatRoomEntity);
//
//        // db 에 저장
//
//        ChatRoomEntity saved = chatService.creatChatRoom1(chatRoomEntity);
//        System.out.println("저장완료");

        System.out.println("room :L " + room);
        log.info("CREATE Chat Room [{}]", room);

        rttr.addFlashAttribute("roomName", room);
//        rttr.addFlashAttribute("roomName", chatRoomEntity);

        return "redirect:/chat";
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId){

        log.info("roomId {}", roomId);
        model.addAttribute("room", chatRepository.findRoomById(roomId));
        return "chatroom";
    }







}
