package com.example.chattproject.controller;

import com.example.chattproject.domain.entity.ChatMessageEntity;
import com.example.chattproject.domain.entity.ChatRoomEntity;
import com.example.chattproject.dto.ChatMessageDetailDTO;
import com.example.chattproject.dto.ChatMessageSaveDTO;
import com.example.chattproject.repository.ChatMessageEntityRepository;
import com.example.chattproject.repository.ChatRoomEntityRepository;
import com.example.chattproject.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template;
    private final ChatRoomEntityRepository crr;
    private final ChatMessageEntityRepository cr;
    private final ChatService cs;

    //Client 가 SEND 할 수 있는 경로
    //stompConfig 에서 설정한 applicationDestinationPrefixes 와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDetailDTO message) {
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");

        System.out.println("message : " + message);

        List<ChatMessageDetailDTO> chatList = cs.findAllChatByRoomId(message.getRoomId());
        System.out.println(chatList);
        if(chatList != null){
            for(ChatMessageDetailDTO c : chatList ){
                ChatMessageDetailDTO messageDetailDTO = new ChatMessageDetailDTO();
                messageDetailDTO.setWriter(c.getWriter());
                messageDetailDTO.setMessage(c.getMessage());
                template.convertAndSend("/sub/chat/room/" + message.getRoomId(), messageDetailDTO);
            }
        }

        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);


        ChatRoomEntity chatRoomEntity= crr.findByRoomId(message.getRoomId());

//        ChatMessageSaveDTO chatMessageSaveDTO = new ChatMessageSaveDTO(message.getRoomId(),message.getWriter(), message.getMessage());
        ChatMessageSaveDTO chatMessageSaveDTO = new ChatMessageSaveDTO();
        chatMessageSaveDTO.setRoomId(message.getRoomId());
        chatMessageSaveDTO.setWriter(message.getWriter());
        chatMessageSaveDTO.setMessage(message.getMessage());

        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setChatRoomEntity(chatRoomEntity);

        chatMessageEntity.setWriter(chatMessageSaveDTO.getWriter());
        chatMessageEntity.setMessage(chatMessageSaveDTO.getMessage());
        chatMessageEntity.setRoomId(message.getRoomId());


//        cr.save(ChatMessageEntity.toChatEntity(chatMessageSaveDTO,chatRoomEntity));
        cr.save(chatMessageEntity);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDetailDTO message) {
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

        // DB에 채팅내용 저장
        ChatRoomEntity chatRoomEntity= crr.findByRoomId(message.getRoomId());

//        ChatMessageSaveDTO chatMessageSaveDTO = new ChatMessageSaveDTO(message.getRoomId(),message.getWriter(), message.getMessage());
        ChatMessageSaveDTO chatMessageSaveDTO = new ChatMessageSaveDTO();
        chatMessageSaveDTO.setRoomId(message.getRoomId());
        chatMessageSaveDTO.setWriter(message.getWriter());
        chatMessageSaveDTO.setMessage(message.getMessage());

        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setChatRoomEntity(chatRoomEntity);

        chatMessageEntity.setWriter(chatMessageSaveDTO.getWriter());
        chatMessageEntity.setMessage(chatMessageSaveDTO.getMessage());
        chatMessageEntity.setRoomId(message.getRoomId());

//        cr.save(ChatMessageEntity.toChatEntity(chatMessageSaveDTO,chatRoomEntity));
        cr.save(chatMessageEntity);

    }
//    @MessageMapping 을 통해 WebSocket 으로 들어오는 메세지 발행을 처리한다.
//    Client 에서는 prefix 를 붙여 "/pub/chat/enter"로 발행 요청을 하면
//    Controller 가 해당 메세지를 받아 처리하는데,
//    메세지가 발행되면 "/sub/chat/room/[roomId]"로 메세지가 전송되는 것을 볼 수 있다.
//    Client 에서는 해당 주소를 SUBSCRIBE 하고 있다가 메세지가 전달되면 화면에 출력한다.
//    이때 /sub/chat/room/[roomId]는 채팅방을 구분하는 값이다.
//    기존의 핸들러 ChatHandler 의 역할을 대신 해주므로 핸들러는 없어도 된다.
}
