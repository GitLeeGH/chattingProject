package com.example.chattproject.service;

import com.example.chattproject.domain.entity.ChatRoomEntity;
import com.example.chattproject.dto.ChatMessageDetailDTO;
import com.example.chattproject.vo.ChatMEmberListVO;
import com.example.chattproject.vo.ChatMessageVO;
import com.example.chattproject.vo.ChatRoomVO;

import java.util.List;

public interface ChatService {

    // 채팅방 개설
    void creatRoom(ChatRoomVO chatRoomVO);

    // 채팅방 생성시 방장 참가
    void joinRoomMaster(ChatMEmberListVO vo);

    // 채팅 리스트 확인
    int chatCheck(ChatMEmberListVO chatMEmberListVO);

    // 채팅방 참가
    void roomJoin(ChatMEmberListVO chatMEmberListVO);

    // 채팅방 정보 조회
    ChatRoomVO selectChattingDetail(int roomId);

    // 메세지 조회
    List<ChatMessageVO> selectMessage(int seq);

    List<ChatMessageDetailDTO> findAllChatByRoomId(String roomId);

    Object findRoomByRoomId(String roomId);


    ChatRoomEntity creatChatRoom1(ChatRoomEntity chatRoomEntity);

    void createChatRoomDTO(String name, String memberNick, Long password);

    Object findAllRooms();
}
