package com.CPGroupH.domains.chat.entity.repository;

import com.CPGroupH.domains.chat.entity.ChatRoom;
import com.CPGroupH.domains.chat.entity.Participant;
import com.CPGroupH.domains.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    boolean existsByChatRoomAndMember(ChatRoom chatRoom, Member member);
}
