package com.CPGroupH.domains.member.controller;

import com.CPGroupH.domains.auth.security.oauth2.entity.CustomOAuth2User;
import com.CPGroupH.domains.member.dto.response.MemberLikeResDTO;
import com.CPGroupH.domains.member.dto.response.MemberMapResDTO;
import com.CPGroupH.domains.member.dto.response.MemberVisitedResDTO;
import com.CPGroupH.domains.member.service.MemberService;
import com.CPGroupH.response.ResponseFactory;
import com.CPGroupH.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Tag(name = "\uD83D\uDC68\u200D\uD83C\uDF3E 사용자", description = "Member API")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/likes")
    @Operation(summary = "좋아요목록", description = "멤버가 좋아요한 장소 목록")
    public ResponseEntity<SuccessResponse<MemberLikeResDTO>> getLikes(
            @AuthenticationPrincipal CustomOAuth2User user
    ){
        Long memberId = user.getMemberId();
        MemberLikeResDTO response = memberService.findLikeByMemberId(memberId);
        return ResponseFactory.success(response);
    }

    @GetMapping("/visited")
    @Operation(summary = "가본 장소 목록", description = "멤버가 가본 장소 목록")
    public ResponseEntity<SuccessResponse<MemberVisitedResDTO>> getVisited(
            @AuthenticationPrincipal CustomOAuth2User user
    ){
        Long memberId = user.getMemberId();
        MemberVisitedResDTO response = memberService.findVisitedByMemberId(memberId);
        return ResponseFactory.success(response);
    }

    @GetMapping("/maps")
    @Operation(summary = "개인 여행 지도", description = "멤버의 개인 여행 지도")
    public ResponseEntity<SuccessResponse<MemberMapResDTO>> getMaps(
            @AuthenticationPrincipal CustomOAuth2User user
    ){
        return null;
    }


}
