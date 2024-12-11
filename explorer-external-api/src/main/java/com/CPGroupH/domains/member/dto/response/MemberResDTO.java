package com.CPGroupH.domains.member.dto.response;

import com.CPGroupH.domains.member.entity.Member;

public record MemberResDTO(
        Long id,
        String nickname,
        String email,
        String profileUrl
) {
    public static MemberResDTO fromEntity(Member member) {
        return new MemberResDTO(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getProfileImage()
        );
    }

    public Member toEntity() {
        return Member.builder()
                .nickname(this.nickname)
                .email(this.email)
                .profileImage(this.profileUrl)
                .build();
    }
}
