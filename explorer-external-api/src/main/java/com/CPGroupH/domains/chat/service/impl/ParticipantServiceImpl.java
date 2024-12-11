package com.CPGroupH.domains.chat.service.impl;

import com.CPGroupH.domains.chat.entity.ChatRoom;
import com.CPGroupH.domains.chat.entity.Participant;
import com.CPGroupH.domains.chat.entity.repository.ChatRoomRepository;
import com.CPGroupH.domains.chat.entity.repository.ParticipantRepository;
import com.CPGroupH.domains.chat.service.ParticipantService;
import com.CPGroupH.domains.member.entity.Member;
import com.CPGroupH.domains.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Participant joinChatRoom(Long chatRoomId, Long memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("Chat room not found"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        boolean isAlreadyParticipant = participantRepository.existsByChatRoomAndMember(chatRoom, member);

        if (isAlreadyParticipant) {
            throw new IllegalStateException("Participant already exists in the chat room");
        }

        Participant participant = Participant.of(chatRoom, member);
        participantRepository.save(participant);

        return participant;
    }

}
