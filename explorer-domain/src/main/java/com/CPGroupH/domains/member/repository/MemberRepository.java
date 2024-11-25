package com.CPGroupH.domains.member.repository;

import com.CPGroupH.domains.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByNickname(String Nickname);

    Optional<Member> findByEmail(String email);
}
