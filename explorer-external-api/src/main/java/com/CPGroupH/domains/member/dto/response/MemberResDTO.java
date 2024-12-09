package com.CPGroupH.domains.member.dto.response;

import com.CPGroupH.common.enums.MemberRole;
import com.CPGroupH.domains.member.entity.Member;

import java.time.LocalDateTime;

public record MemberResDTO(
        Long id,
        String email,
        String profileUrl
) {
    public static MemberResDTO fromEntity(Member member) {
        return new MemberResDTO(
                member.getId(),
                member.getEmail(),
                member.getProfileImage()
        );
    }

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .profileImage(this.profileUrl)
                .build();
    }
}
