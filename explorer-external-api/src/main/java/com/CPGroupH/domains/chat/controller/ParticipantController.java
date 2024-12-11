package com.CPGroupH.domains.chat.controller;

import com.CPGroupH.domains.chat.dto.request.ParticipantReqDTO;
import com.CPGroupH.domains.chat.entity.Participant;
import com.CPGroupH.domains.chat.service.ParticipantService;
import com.CPGroupH.response.ResponseFactory;
import com.CPGroupH.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping("/join")
    public ResponseEntity<SuccessResponse<Participant>> joinChatRoom(@RequestBody ParticipantReqDTO request) {
        Participant participant = participantService.joinChatRoom(request.getChatRoomId(), request.getMemberId());
        return ResponseFactory.success(participant);
    }
}
