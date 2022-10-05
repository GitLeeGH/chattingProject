package com.example.chattproject.repository;

import com.example.chattproject.domain.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomEntityRepository extends JpaRepository<ChatRoomEntity,Long> {

    ChatRoomEntity findByRoomId(String roomId);
}
