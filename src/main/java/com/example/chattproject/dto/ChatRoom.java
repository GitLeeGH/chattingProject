package com.example.chattproject.dto;

import lombok.Data;

import java.util.HashMap;

// Stomp 를 통해 pub/sub 를 사용하면 구독자 관리가 알아서 된다
// 따라서 따로 세션 관리를 하는 코드를 작성할 필요가 없고,
// 메세지를 다른 세션의 클라이언트에게 발송하느 ㄴ것도 구현 필요가 없다!
@Data
public class ChatRoom {

    private String roomId;      // 채팅방 아이디
    private String roomName;    // 채팅방 이름
    private int userCount;      // 채팅방 인원수
    private int maxUserCnt;     // 채팅방 최대 인원 제한

    private String roomPwd;     // 채팅방 삭제시 필요한 password
    private boolean secreteChk; // 채팅방 잠금 여부

    private HashMap<String, String> userList;
}
