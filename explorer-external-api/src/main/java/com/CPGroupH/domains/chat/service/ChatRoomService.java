package com.CPGroupH.domains.chat.service;

import com.CPGroupH.domains.chat.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatRoomService {
    ChatRoom findChatRoomById(String id);

    Page<ChatRoom> getChatRoomsByParticipant(Long participantId, Pageable pageable);

    Page<ChatRoom> getAllChatRooms(Pageable pageable);
}
