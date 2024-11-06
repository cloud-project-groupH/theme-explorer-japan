package com.CPGroupH.domains.auth.security.oauth2.entity.dto;

import com.CPGroupH.common.enums.MemberRole;
import com.CPGroupH.domains.member.entity.Member;
import lombok.Builder;

@Builder
public record OAuth2UserDTO(
        Long memberId,
        String nickname,
        String email,
        String profileImage,
        Boolean allowance,
        MemberRole role
) {
    public static OAuth2UserDTO from(Member member) {
        return new OAuth2UserDTO(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getProfileImage(),
                member.getAllowance(),
                member.getRole()
        );
    }
}
