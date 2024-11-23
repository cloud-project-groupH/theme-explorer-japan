package com.CPGroupH.domains.chat.entity;

import com.CPGroupH.domains.common.entity.BaseEntity;
import com.CPGroupH.domains.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender", nullable = false)
    private Member sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Long createdAt;

    @Builder
    public Message(ChatRoom chatRoom, Member sender, String content, Long createdAt) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.content = content;
        this.createdAt = createdAt;
    }

}
