package com.CPGroupH.domains.chat.entity.repository;

import com.CPGroupH.domains.chat.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.chatRoom.id = :chatRoomId")
    Page<Message> findByChatRoomId(@Param("chatRoomId") Long chatRoomId, Pageable pageable);

}
