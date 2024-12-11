package com.CPGroupH.domains.chat.dto.request;

import java.time.LocalDateTime;

public record CreateChatRoomReqDTO(String title, LocalDateTime travelDate) {
}
