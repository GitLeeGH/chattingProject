package com.example.chattproject.service;



import com.example.chattproject.domain.entity.ChatMessageEntity;
import com.example.chattproject.domain.entity.ChatRoomEntity;
import com.example.chattproject.dto.ChatMessageDetailDTO;
import com.example.chattproject.mapper.ChatMapper;

import com.example.chattproject.repository.ChatMessageEntityRepository;
import com.example.chattproject.repository.ChatRoomEntityRepository;
import com.example.chattproject.vo.ChatMEmberListVO;
import com.example.chattproject.vo.ChatMessageVO;
import com.example.chattproject.vo.ChatRoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChatServiceImpl implements ChatService{


    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private ChatRoomEntityRepository chatRoomEntityRepository;

    @Autowired
    private ChatMessageEntityRepository chatMessageEntityRepository;


    @Override
    public void creatRoom(ChatRoomVO chatRoomVO) {

        chatMapper.creatRoom(chatRoomVO);
    }

    @Override
    public void joinRoomMaster(ChatMEmberListVO vo) {

        chatMapper.joinRoomMaster(vo);

    }

    @Override
    public int chatCheck(ChatMEmberListVO chatMEmberListVO) {
        return chatMapper.chatCheck(chatMEmberListVO);
    }

    @Override
    public void roomJoin(ChatMEmberListVO chatMEmberListVO) {
        chatMapper.roomJoin(chatMEmberListVO);
    }

    @Override
    public ChatRoomVO selectChattingDetail(int roomId) {
        return null;
    }

    @Override
    public List<ChatMessageVO> selectMessage(int seq) {
        return null;
    }

    @Override
    public List<ChatMessageDetailDTO> findAllChatByRoomId(String roomId) {

        System.out.println("채팅 리스트 불러오기");
       List<ChatMessageEntity> list = chatMessageEntityRepository.findAllByRoomId(roomId);

       List<ChatMessageDetailDTO> list1 = new ArrayList<>();
       for(ChatMessageEntity chatMessageEntity : list){
           ChatMessageDetailDTO chatMessageDetailDTO = new ChatMessageDetailDTO();
           chatMessageDetailDTO.setChatId(chatMessageEntity.getId());
           chatMessageDetailDTO.setChatRoomId(chatMessageEntity.getChatRoomEntity().getId());
           chatMessageDetailDTO.setRoomId(chatMessageEntity.getChatRoomEntity().getRoomId());

           chatMessageDetailDTO.setWriter(chatMessageEntity.getWriter());
           chatMessageDetailDTO.setMessage(chatMessageEntity.getMessage());

           list1.add(chatMessageDetailDTO);

       }
       for (ChatMessageDetailDTO chatMessageDetailDTO : list1){
           System.out.println("chatMessageDetailDTO 채팅 리스트 : " + chatMessageDetailDTO );
       }

        System.out.println("채팅 리스트 불러오기 완료");
//        System.out.println(list);
        // 채팅 리스트 못불러옴

//        return chatMessageEntityRepository.findAllByRoomId(roomId);
        return list1;
    }


    @Override
    public Object findRoomByRoomId(String roomId) {

        return chatRoomEntityRepository.findByRoomId(roomId);
    }

    @Override
    public ChatRoomEntity creatChatRoom1(ChatRoomEntity chatRoomEntity) {

        ChatRoomEntity chatRoomEntity1 = chatRoomEntityRepository.save(chatRoomEntity);
        return chatRoomEntity1;
    }

    @Override
    public void createChatRoomDTO(String roomName, String memberNick, Long password) {

        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        chatRoomEntity.setRoomId(UUID.randomUUID().toString());
        chatRoomEntity.setRoomName(roomName);
        chatRoomEntity.setChatMentor(memberNick);
        chatRoomEntity.setPassword(password);
        System.out.println("저장 완료");
        chatRoomEntityRepository.save(chatRoomEntity);


    }

    @Override
    public Object findAllRooms() {

        return chatRoomEntityRepository.findAll();

    }


}
