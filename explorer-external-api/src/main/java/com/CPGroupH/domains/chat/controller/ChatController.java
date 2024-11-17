package com.CPGroupH.domains.chat.controller;

import com.CPGroupH.domains.chat.dto.request.MessageReqDTO;
import com.CPGroupH.domains.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage/{chatRoomId}")
    public void sendMessageToRoom(@DestinationVariable String chatRoomId, @Payload MessageReqDTO chatMessage) {
        chatService.sendMessageToRoom(chatRoomId, chatMessage);
    }
}
