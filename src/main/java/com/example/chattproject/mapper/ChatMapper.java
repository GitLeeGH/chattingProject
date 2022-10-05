package com.example.chattproject.mapper;

import com.example.chattproject.vo.ChatMEmberListVO;
import com.example.chattproject.vo.ChatRoomVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {

    // 채팅방 개설
    void creatRoom(ChatRoomVO vo);

    // 채팅방 생성시 방장 참가
    void joinRoomMaster(ChatMEmberListVO vo);


    int chatCheck(ChatMEmberListVO chatMEmberListVO);

    void roomJoin(ChatMEmberListVO chatMEmberListVO);
}
