package com.CPGroupH.domains.place.controller;

import com.CPGroupH.domains.auth.security.oauth2.entity.CustomOAuth2User;
import com.CPGroupH.domains.place.dto.response.PersonalPlaceResDTO;
import com.CPGroupH.domains.place.dto.response.PopularPlaceResDTO;
import com.CPGroupH.domains.place.dto.response.SearchPlaceResDTO;
import com.CPGroupH.domains.place.service.PlaceService;
import com.CPGroupH.response.ResponseFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/{placeId}/likes")
    public ResponseEntity<Void> like(@PathVariable("placeId") Long placeId, @AuthenticationPrincipal CustomOAuth2User user) {
        placeService.like(user.getMemberId(), placeId);
        return ResponseFactory.noContent();
    }

    @PostMapping("/{placeId}/visited")
    public ResponseEntity<Void> visited(@PathVariable("placeId") Long placeId, @AuthenticationPrincipal CustomOAuth2User user) {
        placeService.visited(user.getMemberId(), placeId);
        return ResponseFactory.noContent();
    }

    @GetMapping("/popular")
    public ResponseEntity<List<PopularPlaceResDTO>> getPopularPlaces(@AuthenticationPrincipal CustomOAuth2User user) {
        log.warn("Get popular places{}", user.getMemberId());
        List<PopularPlaceResDTO> response = placeService.popularPlace();
        log.info("Popular places: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/personal")
    public ResponseEntity<List<PersonalPlaceResDTO>> getPersonalPlaces(@AuthenticationPrincipal CustomOAuth2User user) {
        List<PersonalPlaceResDTO> response = placeService.personalPlace(user.getMemberId());
        log.info("Personal places: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchPlaceResDTO>> getSearchPlaces(@RequestParam String keyword) {
        List<SearchPlaceResDTO> response = placeService.searchPlace(keyword);
        return ResponseEntity.ok(response);
    }
}
