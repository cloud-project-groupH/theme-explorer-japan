package com.CPGroupH.domains.auth.security.oauth2.entity;

import com.CPGroupH.common.enums.MemberRole;
import com.CPGroupH.domains.auth.security.oauth2.entity.dto.OAuth2UserDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final OAuth2UserDTO oauth2UserDTO;

    @Override
    public <A> A getAttribute(String name) {
        return (A) getAttributes().get(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
                "nickname", oauth2UserDTO.nickname(),
                "email", oauth2UserDTO.email(),
                "profileImage", oauth2UserDTO.profileImage(),
                "allowance", oauth2UserDTO.allowance()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> oauth2UserDTO.role()
                .getRoleName());
        return collection;
    }

    @Override
    public String getName() {
        return oauth2UserDTO.email();
    }

    public Long getMemberId(){
        return oauth2UserDTO.memberId();
    }

    public String getNickname(){
        return oauth2UserDTO.nickname();
    }

    public String getEmail(){
        return oauth2UserDTO.email();
    }

    public String getProfileImage(){
        return oauth2UserDTO.profileImage();
    }

    public Boolean getAllowance(){
        return oauth2UserDTO.allowance();
    }

    public MemberRole getRole(){
        return oauth2UserDTO.role();
    }
}
