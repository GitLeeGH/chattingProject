package com.example.chattproject.repository;

import com.example.chattproject.domain.entity.ChatMessageEntity;

import com.example.chattproject.dto.ChatMessageDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageEntityRepository extends JpaRepository<ChatMessageEntity,Long> {
    List<ChatMessageEntity> findAllByRoomId(String roomId);
}
