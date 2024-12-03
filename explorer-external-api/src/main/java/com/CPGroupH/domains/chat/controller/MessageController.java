package com.CPGroupH.domains.chat.controller;

import com.CPGroupH.domains.chat.entity.Message;
import com.CPGroupH.domains.chat.service.MessageService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
@Tag(name = "Message", description = "Message API")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "메세지 가져오기")
    @GetMapping("/")
    public ResponseEntity<SuccessResponse<Page<Message>>> getMessages(@RequestParam(value = "chatRoomId") Long chatRoomId,
                                                                      @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                                      @RequestParam(value = "limit", defaultValue = "100", required = false) Integer limit,
                                                                      @RequestParam(value = "sort", defaultValue = "createdAt") String sort,
                                                                      @RequestParam(value = "order", defaultValue = "desc") String order) {

        Pageable pageable = PageRequest.of(
                page,
                limit,
                "desc".equalsIgnoreCase(order) ? Sort.by(sort).descending() : Sort.by(sort).ascending()
        );

        Page<Message> messages = messageService.getMessagesByChatRoomId(chatRoomId, pageable);
        return ResponseFactory.success(messages);
    }
}
