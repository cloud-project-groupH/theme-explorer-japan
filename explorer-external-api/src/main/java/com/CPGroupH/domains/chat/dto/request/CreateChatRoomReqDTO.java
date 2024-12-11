package com.CPGroupH.domains.chat.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public record CreateChatRoomReqDTO(
        String title,
        LocalDateTime travelDate,
        List<Long> placeIds // 장소 ID 리스트
) {
}