package com.CPGroupH.domains.auth.controller;

import com.CPGroupH.common.enums.MemberRole;
import com.CPGroupH.domains.auth.dto.request.AllowanceRequest;
import com.CPGroupH.domains.auth.dto.response.AllowanceResponse;
import com.CPGroupH.domains.auth.dto.response.RefreshResponse;
import com.CPGroupH.domains.auth.service.AuthService;
import com.CPGroupH.domains.auth.service.CookieService;
import com.CPGroupH.domains.auth.service.JwtService;
import com.CPGroupH.domains.member.entity.Member;
import com.CPGroupH.domains.member.repository.MemberRepository;
import com.CPGroupH.response.ResponseFactory;
import com.CPGroupH.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "\uD83D\uDD11 인증", description = "Auth API")
public class AuthController {
    private final AuthService authService;
    private final CookieService cookieService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    RestTemplate restTemplate = new RestTemplate(); // Kakao API 호출용

    @Operation(summary = "accessToken 재발급")
    @PostMapping("/token/reissue")
    public ResponseEntity<SuccessResponse<RefreshResponse>> reissueToken(HttpServletRequest request) {
        String refreshToken = cookieService.getRefreshTokenCookie(request);
        var result = authService.reissueToken(refreshToken);
        return ResponseFactory.success(result);
    }

    @Operation(summary = "설문 조사")
    @PostMapping("/terms")
    public ResponseEntity<SuccessResponse<AllowanceResponse>> agreeToTerms(
            @RequestBody AllowanceRequest request,
            HttpServletResponse response
    ) throws IOException {
        var result = authService.updateAllowance(request.token());
        return ResponseFactory.success(result);
    }

    @Operation(summary = "카카오 로그인 처리 (Flutter SDK에서 받은 토큰 처리)")
    @PostMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(HttpServletRequest request) {
        try {
            var tokens = authService.handleKakaoLogin(request);
            return ResponseEntity.ok(tokens);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("카카오 사용자 정보 조회 실패");
        }
    }
}