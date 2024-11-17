package com.CPGroupH.domains.chat.service;

import com.CPGroupH.domains.chat.dto.request.MessageReqDTO;

public interface ChatService {
    void sendMessageToRoom(String chatRoomId, MessageReqDTO chatMessage);
}
