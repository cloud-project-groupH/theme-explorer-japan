package com.CPGroupH.domains.place.service;

import com.CPGroupH.domains.auth.security.oauth2.entity.CustomOAuth2User;

public interface PlaceService {
    void like(Long memberId, Long placeId);
    void visited(Long memberId, Long placeId);
}
