package com.CPGroupH.domains.chat.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParticipantReqDTO {
    private Long chatRoomId;
    private Long memberId;
}

