package com.CPGroupH.domains.chat.service.impl;

import com.CPGroupH.domains.chat.dto.request.MessageReqDTO;
import com.CPGroupH.domains.chat.service.ChatService;
import com.CPGroupH.domains.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MemberService memberService;

    public void sendMessageToRoom(String chatRoomId, MessageReqDTO chatMessage) {
//        Member sender = memberService.findMemberByNickname(chatMessage.sender());
//        Message message = chatMessage.toEntity(sender);
//        TODO: Message -> DB에 저장
        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoomId, chatMessage);
    }

}
