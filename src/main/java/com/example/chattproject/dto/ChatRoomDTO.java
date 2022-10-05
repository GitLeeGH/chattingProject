package com.example.chattproject.dto;

import com.example.chattproject.domain.entity.ChatRoomEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {

    private String chatMentor;
    private String roomId;
    private String name;

    private Long password;

    private Set<WebSocketSession> sessions = new HashSet<>();
    //WebSocketSession 은 Spring 에서 Websocket Connection 이 맺어진 세션

    public static ChatRoomDTO create(String name,Long password){
        ChatRoomDTO room = new ChatRoomDTO();

        room.roomId = UUID.randomUUID().toString();
        room.name = name;
        room.password = password;
        return room;
    }


//
//    private String roomId;
//
//    private String roomName;
//
//    private String roomPwd;
//
//    private boolean secretChk;
//
//    private int maxUserCnt;
//
//    // user list
////    private HashMap<String,String> members;
//
//    private int userCount;
//
//    public ChatRoomDTO(String roomName, String roomPwd, boolean secretChk, int maxUserCnt, HashMap<String,String> members, int userCount) {
//
//        this.roomName = roomName;
//        this.roomPwd = roomPwd;
//        this.secretChk = secretChk;
//        this.maxUserCnt = maxUserCnt;
////        this.members = members;
//        this.userCount = userCount;
//    }
//
//    @Override
//    public String toString() {
//        return "ChatRoomDTO{" +
//
//                ", roomName='" + roomName + '\'' +
//                ", roomPwd='" + roomPwd + '\'' +
//                ", secretChk=" + secretChk +
//                ", maxUserCnt=" + maxUserCnt +
////                ", members=" + members +
//                ", userCount=" + userCount +
//                '}';
//    }
//
//    public ChatRoomEntity toEntity(){
//        return new ChatRoomEntity(null,roomName,roomPwd,secretChk,maxUserCnt,userCount);
//    }
}
