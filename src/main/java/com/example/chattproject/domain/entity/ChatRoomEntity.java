package com.example.chattproject.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "chatRoom_table")
public class ChatRoomEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoom_id")
    private Long id;

    @Column
    private String roomId;

    @Column
    private String roomName;

    @Column
    private Long password;

    @Column
    private String buyer;

    @Column
    private String seller;

    @OneToMany(mappedBy = "chatRoomEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessageEntity> chatMessageEntityList = new ArrayList<>();


    public static ChatRoomEntity toChatRoomEntity(String roomName, String roomId, String seller,Long password){
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        chatRoomEntity.setRoomName(roomName);
        chatRoomEntity.setRoomId(roomId);
        chatRoomEntity.setSeller(seller);
        chatRoomEntity.setPassword(password);
        return chatRoomEntity;
    }


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "chatRoom_id")
//    private Long id;
//
//    @Column
//    private String roomId;
//
//    @Column
//    private String roomName;
//
//    @Column
//    private String seller;
//
//    @Column
//    private String roomPwd;
//
//    @Column
//    private boolean secretChk;
//
//    @Column
//    private int maxUserCnt;
//
//
//
//    // user list
////    @ElementCollection
////    private HashMap<String,String> members;
//
//    @Column
//    private int userCount;
//
//
//
//
//    public ChatRoomEntity(Long id, String roomId, String roomPwd, boolean secretChk, int maxUserCnt, int userCount) {
//        this.id = id;
//        this.roomId = roomId;
//        this.roomPwd = roomPwd;
//        this.secretChk = secretChk;
//        this.maxUserCnt = maxUserCnt;
//        //this.members = members;
//        this.userCount = userCount;
//
//    }
//
//    @Override
//    public String toString() {
//        return "ChatRoomEntity{" +
//                "id=" + id +
//                ", roomId='" + roomId + '\'' +
//                ", roomName='" + roomName + '\'' +
//                ", roomPwd='" + roomPwd + '\'' +
//                ", secretChk=" + secretChk +
//                ", maxUserCnt=" + maxUserCnt +
////                ", members=" + members +
//                ", userCount=" + userCount +
//                '}';
//    }
//
//    //    @OneToMany(mappedBy = "chatRoomEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
////    private List<ChatMessageEntity> chatMessageEntityList = new ArrayList<>();



}
