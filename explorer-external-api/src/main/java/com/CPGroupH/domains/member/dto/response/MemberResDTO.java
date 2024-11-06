package com.CPGroupH.domains.member.dto.response;

import com.CPGroupH.common.enums.MemberRole;
import com.CPGroupH.domains.member.entity.Member;

import java.time.LocalDateTime;

public record MemberResDTO(Long id, String email, String profileUrl, MemberRole role, LocalDateTime deleteAt) {
    public static MemberResDTO fromEntity(Member member) {
        return new MemberResDTO(
                member.getId(),
                member.getEmail(),
                member.getProfileImage(),
                member.getRole(),
                member.getDeletedAt()
        );
    }

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .profileImage(this.profileUrl)
                .role(this.role)
                .build();
    }
}
