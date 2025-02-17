package com.CPGroupH.domains.auth.security.oauth2.entity;

import com.CPGroupH.domains.auth.security.oauth2.enums.SocialType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuth2Attributes {
    private final Map<String, Object> attributes;
    private final String email;
    private final String nickname;
    private final String profileImage;

    @Builder(access = AccessLevel.PRIVATE)
    private OAuth2Attributes(Map<String, Object> attributes, String email, String nickname, String profileImage) {
        this.attributes = attributes;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static OAuth2Attributes of(SocialType socialType, Map<String, Object> attributes) {
        return switch (socialType) {
            case KAKAO -> ofKakao(attributes);
        };
    }

    private static OAuth2Attributes ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, String> profile = (Map<String, String>) kakaoAccount.get("profile");
        return OAuth2Attributes.builder().
                email(String.valueOf(kakaoAccount.get("email")))
                .nickname(profile.get("nickname"))
                .profileImage(profile.get("profile_image_url"))
                .build();
    }
}

