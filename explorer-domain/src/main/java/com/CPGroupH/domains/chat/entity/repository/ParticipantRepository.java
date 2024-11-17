package com.CPGroupH.domains.chat.entity.repository;

import com.CPGroupH.domains.chat.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
