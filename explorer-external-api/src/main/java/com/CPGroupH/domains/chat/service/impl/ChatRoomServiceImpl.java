package com.CPGroupH.domains.chat.service.impl;

import com.CPGroupH.domains.chat.entity.ChatRoom;
import com.CPGroupH.domains.chat.entity.repository.ChatRoomRepository;
import com.CPGroupH.domains.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom findChatRoomById(String id) {
        return chatRoomRepository.findChatRoomById(Long.parseLong(id)).orElseThrow(() -> new NoSuchElementException("ChatRoom이 없습니다"));
    }

}
