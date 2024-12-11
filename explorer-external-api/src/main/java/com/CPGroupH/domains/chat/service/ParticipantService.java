package com.CPGroupH.domains.chat.service;

import com.CPGroupH.domains.chat.entity.Participant;

public interface ParticipantService {
    Participant joinChatRoom(Long chatRoomId, Long memberId);
}
