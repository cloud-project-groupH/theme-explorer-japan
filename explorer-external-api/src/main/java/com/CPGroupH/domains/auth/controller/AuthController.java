package com.CPGroupH.domains.auth.controller;

import com.CPGroupH.domains.auth.dto.request.AllowanceRequest;
import com.CPGroupH.domains.auth.dto.response.AllowanceResponse;
import com.CPGroupH.domains.auth.dto.response.RefreshResponse;
import com.CPGroupH.domains.auth.service.AuthService;
import com.CPGroupH.domains.auth.service.CookieService;
import com.CPGroupH.domains.auth.service.JwtService;
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
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization: " + request.getHeader("Authorization"));
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("No Bearer token provided");
        }

        String kakaoAccessToken = authorizationHeader.substring(7); // 'Bearer ' 제거

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(kakaoAccessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> kakaoResponse = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                entity,
                Map.class
        );

        if (kakaoResponse.getStatusCode() == HttpStatus.OK) {
            Map<String,Object> kakaoUserInfo = kakaoResponse.getBody();
            Long kakaoId = ((Number) kakaoUserInfo.get("id")).longValue();

            // 사용자 식별 로직 (DB 조회/생성)
            // User user = userService.findOrCreateUserByKakaoId(kakaoId);
            // long userId = user.getId();

            // 예시로 kakaoId 자체를 userId로 사용
            long userId = kakaoId;

            String accessToken = jwtService.createAccessToken(userId);
            String refreshToken = jwtService.createRefreshToken(userId);

            Map<String,String> response = new HashMap<>();
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("카카오 사용자 정보 조회 실패");
        }
    }
}