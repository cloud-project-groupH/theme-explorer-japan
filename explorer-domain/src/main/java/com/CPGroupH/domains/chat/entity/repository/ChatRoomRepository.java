package com.CPGroupH.domains.chat.entity.repository;

import com.CPGroupH.domains.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    
    Optional<ChatRoom> findChatRoomById(Long id);
}
