package com.CPGroupH.domains.chat.controller;

import com.CPGroupH.domains.chat.dto.request.MessageReqDTO;
import com.CPGroupH.domains.chat.service.ChatWebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatWebSocketService chatWebSocketService;

    @MessageMapping("/chat.sendMessage/{chatRoomId}")
    public void sendMessageToRoom(@DestinationVariable String chatRoomId, @Payload MessageReqDTO chatMessage) {
        chatWebSocketService.sendMessageToRoom(chatRoomId, chatMessage);
    }
}
