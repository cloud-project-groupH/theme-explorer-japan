package com.CPGroupH.domains.chat.service.impl;

import com.CPGroupH.domains.chat.entity.Message;
import com.CPGroupH.domains.chat.entity.repository.MessageRepository;
import com.CPGroupH.domains.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServcieImpl implements MessageService {

    private final MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public Page<Message> getMessagesByChatRoomId(Long chatRoomId, Pageable pageable) {
        return messageRepository.findByChatRoomId(chatRoomId, pageable);
    }

}
