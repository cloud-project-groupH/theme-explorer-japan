package com.CPGroupH.domains.chat.controller;

import com.CPGroupH.domains.chat.dto.request.CreateChatRoomReqDTO;
import com.CPGroupH.domains.chat.entity.ChatRoom;
import com.CPGroupH.domains.chat.service.ChatRoomService;
import com.CPGroupH.response.ResponseFactory;
import com.CPGroupH.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatrooms")
@Tag(name = "ChatRoom", description = "ChatRoom API")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 가져오기")
    @GetMapping("/")
    public ResponseEntity<SuccessResponse<Page<ChatRoom>>> getChatRooms(@RequestParam(value = "participantId", required = false) Long participantId,
                                                                        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                                        @RequestParam(value = "limit", required = false) Integer limit,
                                                                        @RequestParam(value = "sort", defaultValue = "createdAt") String sort,
                                                                        @RequestParam(value = "order", defaultValue = "desc") String order) {

        Pageable pageable = PageRequest.of(page, limit != null ? limit : 10,
                "desc".equalsIgnoreCase(order) ? Sort.by(sort).descending() : Sort.by(sort).ascending());

        Page<ChatRoom> chatRooms = participantId != null
                ? chatRoomService.getChatRoomsByParticipant(participantId, pageable)
                : chatRoomService.getAllChatRooms(pageable);

        return ResponseFactory.success(chatRooms);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<ChatRoom>> createChatRoom(@RequestBody CreateChatRoomReqDTO request) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(request.title(), request.travelDate());
        return ResponseFactory.created(chatRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable Long id) {
        chatRoomService.deleteChatRoom(id);
        return ResponseFactory.noContent();
    }
}
