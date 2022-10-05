package com.example.chattproject.domain.entity;

import com.example.chattproject.dto.ChatDTO;
import com.example.chattproject.dto.ChatMessageSaveDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "chat_table")
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_Room_Id")
    private ChatRoomEntity chatRoomEntity;

    private String writer;

    @Column
    private String message;

    @Column
    private String roomId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime sendDate;

    public static ChatMessageEntity toChatEntity(ChatMessageSaveDTO chatMessageSaveDTO, ChatRoomEntity chatRoomEntity){
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();

        chatMessageEntity.setChatRoomEntity(chatRoomEntity);

        chatMessageEntity.setWriter(chatMessageSaveDTO.getWriter());
        chatMessageEntity.setMessage(chatMessageSaveDTO.getMessage());

        return chatMessageEntity;

    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "chat_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "chatRoom_id")
//    private ChatRoomEntity chatRoomEntity;
//
//    private String writer;
//
//    @Column
//    private String message;
//
//    @CreationTimestamp
//    @Column(updatable = false)
//    private LocalDateTime sendDate;
//
//    @Column
//    private ChatDTO.MessageType type;   // 메세지 타입
//
//    @Column
//    private String roomId;      // 방 번호
//
//    @Column
//    private String sender;      // 채팅을 보내는 사람
//
//
//
//
//
//    public static ChatMessageEntity toChatEntity(ChatMessageSaveDTO chatMessageSaveDTO, ChatRoomEntity chatRoomEntity){
//
//        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
//
//        chatMessageEntity.setChatRoomEntity(chatRoomEntity);
//
//        chatMessageEntity.setWriter(chatMessageEntity.getWriter());
//        chatMessageEntity.setMessage(chatMessageEntity.getMessage());
//
//        return chatMessageEntity;
//    }



}
