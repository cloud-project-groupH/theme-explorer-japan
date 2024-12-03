package com.CPGroupH.domains.chat.entity.repository;

import com.CPGroupH.domains.chat.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findChatRoomById(Long id);

    @Query("SELECT cr FROM ChatRoom cr JOIN cr.participants p WHERE p.id = :participantId")
    Page<ChatRoom> findByParticipantId(@Param("participantId") Long participantId, Pageable pageable);
}
