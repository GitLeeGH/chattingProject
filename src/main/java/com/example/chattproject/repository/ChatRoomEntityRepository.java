package com.example.chattproject.repository;

import com.example.chattproject.domain.entity.ChatMessageEntity;
import com.example.chattproject.domain.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomEntityRepository extends JpaRepository<ChatRoomEntity,Long> {

    ChatRoomEntity findByRoomId(String roomId);


    List<ChatRoomEntity> findAllBySeller(String seller);

    List<ChatRoomEntity> findAllByBuyer(String seller);
}
