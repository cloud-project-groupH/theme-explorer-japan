package com.CPGroupH.domains.chat.dto.request;

import com.CPGroupH.domains.chat.entity.ChatRoom;
import com.CPGroupH.domains.chat.entity.Message;
import com.CPGroupH.domains.member.entity.Member;

public record MessageReqDTO(String sender, String contents) {
    //    public static MessageReqDTO fromEntity(Message message) {
//        return new MessageReqDTO(
//                message.getSender(),
//                message.getContent()
//        );
//    }
//
    public Message toEntity(Member sender, ChatRoom chatRoom) {
        return Message.builder()
                .sender(sender)
                .chatRoom(chatRoom)
                .content(this.contents)
                .build();
    }
}
