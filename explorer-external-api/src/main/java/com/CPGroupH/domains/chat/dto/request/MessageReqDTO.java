package com.CPGroupH.domains.chat.dto.request;

import com.CPGroupH.domains.chat.entity.Message;
import com.CPGroupH.domains.member.entity.Member;

public record MessageReqDTO(String sender, String content, Long createdAt) {
    //    public static MessageReqDTO fromEntity(Message message) {
//        return new MessageReqDTO(
//                message.getSender(),
//                message.getContent()
//        );
//    }
//
    public Message toEntity(Member sender) {
        return Message.builder()
                .sender(sender)
                .content(this.content)
                .createdAt(this.createdAt)
                .build();
    }
}
