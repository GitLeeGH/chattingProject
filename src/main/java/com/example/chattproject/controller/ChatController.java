package com.example.chattproject.controller;

import com.example.chattproject.dao.ChatRepository;
import com.example.chattproject.domain.entity.ChatRoomEntity;
import com.example.chattproject.dto.ChatDTO;
import com.example.chattproject.dto.ChatRoom;
import com.example.chattproject.dto.ChatRoomDTO;
import com.example.chattproject.repository.ChatRoomEntityRepository;
import com.example.chattproject.service.ChatService;
import com.example.chattproject.vo.ChatMEmberListVO;
import com.example.chattproject.vo.ChatMessageVO;
import com.example.chattproject.vo.ChatRoomVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * ChatController
 * 채팅을 수신(sub)하고, 송신(pub) 하기 위한 Controller
 * @MessageMapping
 * Stomp 에서 들어오는 message 를 서버에서 발송(pub) 한 메시지가 도착하는 엔드 포인드
 * /chat/enterUser 로 되어 있지만 실제로는 앞에 /pub 가 생략되어있다.
 * 즉 클라이언트가 /pub/chat/enterUser 로 메시지를 발송하면 @MessageMapping 에 의해서 아래의 해당
 * 어노테이션이 달린 메서드가 실행된다.
 *
 * convertAndSend()
 * 매개변수로 각가 메시지의 도착 지점과 객체를 넣어준다.
 * 이를 통해 도착 지점 즉 sub 되는 지점으로 인자로 들어온 객체를 Message 객체로 변환해서
 * 해당 도착지점을 sub 하고 있는 모든 사용자에게 메시지를 보내준다다
*/
@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    // 아래에서 사용되는 convertAndSend 를 사용하기 위해서 서언
    // convertAndSend 는 객체를 인자로 넘겨주면 자동으로 Message 객체로 변환 후 도착지로 전송한다.
    private final SimpMessageSendingOperations template;
    private final ChatService cs;

    @Autowired
    ChatRoomEntityRepository chatRoomEntityRepository;

    @Inject     // byType으로 자동 주입
    ChatService service;

    @Autowired
    ChatRepository repository;

    // MessageMapping 을 통해 webSocket 로 들어오는 메시지를 발신 처리한다.
    // 이때 클라이언트에서는 /pub/chat/message 로 요청하게 되고 이것을 controller 가 받아서 처리한다.
    // 처리가 완료되면 /sub/chat/room/roomId 로 메시지가 전송된다.
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDTO chat, SimpMessageHeaderAccessor headerAccessor) {

        // 채팅방 유저+1
        repository.plusUserCnt(chat.getRoomId());

        // 채팅방에 유저 추가 및 UserUUID 반환
        String userUUID = repository.addUser(chat.getRoomId(), chat.getSender());

        // 반환 결과를 socket session 에 userUUID 로 저장
        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        chat.setMessage(chat.getSender() + " 님 입장!!");
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);

    }

    // 해당 유저
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDTO chat) {
        log.info("CHAT {}", chat);
        chat.setMessage(chat.getMessage());
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);

//        ChatRoomEntity chatRoomEntity = chatRoomEntityRepository.findByRoomId(chat.getRoomId());

    }

    // 유저 퇴장 시에는 EventListener 을 통해서 유저 퇴장을 확인
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("DisConnEvent {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        log.info("headAccessor {}", headerAccessor);

        // 채팅방 유저 -1
//        repository.minusUserCnt(roomId);

        // 채팅방 유저 리스트에서 UUID 유저 닉네임 조회 및 리스트에서 유저 삭제
        String username = repository.getUserName(roomId, userUUID);
        repository.delUser(roomId, userUUID);

        if (username != null) {
            log.info("User Disconnected : " + username);

            // builder 어노테이션 활용
            ChatDTO chat = ChatDTO.builder()
                    .type(ChatDTO.MessageType.LEAVE)
                    .sender(username)
                    .message(username + " 님 퇴장!!")
                    .build();

            template.convertAndSend("/sub/chat/room/" + roomId, chat);
        }
    }

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/chat/userlist")
    @ResponseBody
    public ArrayList<String> userList(String roomId) {

        return repository.getUserList(roomId);
    }

    // 채팅에 참여한 유저 닉네임 중복 확인
    @GetMapping("/chat/duplicateName")
    @ResponseBody
    public String isDuplicateName(@RequestParam("roomId") String roomId, @RequestParam("username") String username) {

        // 유저 이름 확인
        String userName = repository.isDuplicateName(roomId, username);
        log.info("동작확인 {}", userName);

        return userName;
    }

    // 채팅방 참가
    @RequestMapping(value = "/roomJoin")
    public String roomJoin(@RequestParam("roomId") int roomId, Principal principal, ChatMEmberListVO chatMEmberListVO, Model model, HttpServletRequest request){

        log.info("room join -----------------------------------");

        // 로그인 아이디
        chatMEmberListVO.setMemberId(principal.getName());
        if(service.chatCheck(chatMEmberListVO) == 0) {
            service.roomJoin(chatMEmberListVO);
        }

        // 채팅방 정보 불러오기
        ChatRoomVO chatRoomVO = service.selectChattingDetail(roomId);
        model.addAttribute("selectMyChatting", chatRoomVO);
        model.addAttribute("chatCheck",service.chatCheck(chatMEmberListVO));

        System.out.println("model : " + model);

        return "redirect:/selectMessage?roomId="+ roomId;
    }

    // 해당 방의 메세지 조회
    @RequestMapping("/selectMessage")
    public String selectMessage(@RequestParam("roomId") int seq, Model model) {
        List<ChatMessageVO> cm = service.selectMessage(seq);
        model.addAttribute("selectMessage", cm);
        ChatRoomVO cr = service.selectChattingDetail(seq);
        model.addAttribute("selectChattingDetail", cr);
        return "chatting/chattingdetail";
    }

//    @RequestMapping("/selectMessage")
//    public String selectMessage(@RequestParam("roomId") int roomId, Model model){
//
//    }


    //나의 채팅방 조회
    @GetMapping("/mentoring/myMentoring/{memberId}")
    public String myMentoring(@PathVariable ("memberId") String memberId,Model model,Principal principal){

        String seller = principal.getName();
        // 채팅창 목록 불러오기
        model.addAttribute("rooms1",cs.findRoomByBuyer(seller));
        model.addAttribute("rooms",cs.findRoomBySeller(seller));

        System.out.println(model);



        return "mentoring/myMentoring";
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public String create(@RequestParam String roomName, @RequestParam Long password, @RequestParam String writer, HttpSession session,Model model, Principal principal){
//        Long memberId = (Long) session.getAttribute(LOGIN_ID);
        String memberNick = principal.getName();

        log.info("# Create Chat Room , name: " + roomName);

        cs.createChatRoomDTO(roomName,memberNick,password,writer);
        System.out.println("저장완료");

        return "redirect:/mentoring/myMentoring/"+principal.getName();
    }

    //채팅방 조회
    @GetMapping("/room")
    public void getRoom(String roomId, Model model,HttpSession session , Principal principal){
        //        Long memberId = (Long) session.getAttribute(LOGIN_ID);
        //        String memberProfileName = ms.findById(memberId).getMemberProfileName();
        //        model.addAttribute("memberProfileName",memberProfileName);
        System.out.println("여기 오류니?");
        log.info("# get Chat Room, roomID : " + roomId);


        ChatRoomEntity chatRoomEntity = (ChatRoomEntity) cs.findRoomByRoomId(roomId);
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.setRoomId(chatRoomEntity.getRoomId());
        chatRoomDTO.setSeller(chatRoomEntity.getSeller());
        chatRoomDTO.setName(chatRoomEntity.getRoomName());
        chatRoomDTO.setPassword(chatRoomEntity.getPassword());

        System.out.println(chatRoomDTO);

//        model.addAttribute("room", cs.findRoomByRoomId(roomId));
        model.addAttribute("room",chatRoomEntity);

    }








}
