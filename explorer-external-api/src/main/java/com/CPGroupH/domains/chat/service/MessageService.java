package com.CPGroupH.domains.chat.service;

import com.CPGroupH.domains.chat.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Message saveMessage(Message message);

    Page<Message> getMessagesByChatRoomId(Long chatRoomId, Pageable pageable);
}
