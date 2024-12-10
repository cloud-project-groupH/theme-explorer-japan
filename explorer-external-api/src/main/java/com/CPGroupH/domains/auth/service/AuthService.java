package com.CPGroupH.domains.auth.service;

import com.CPGroupH.common.enums.MemberRole;
import com.CPGroupH.domains.auth.dto.response.AllowanceResponse;
import com.CPGroupH.domains.auth.dto.response.RefreshResponse;
import com.CPGroupH.domains.auth.mapper.AuthMapper;
import com.CPGroupH.domains.member.entity.Member;
import com.CPGroupH.domains.member.repository.MemberRepository;
import com.CPGroupH.error.code.AuthErrorCode;
import com.CPGroupH.error.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final RedisAuthService redisAuthService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final AuthMapper authMapper;
    RestTemplate restTemplate = new RestTemplate();

    public RefreshResponse reissueToken(String refreshToken) {
        log.info("[AUTH_INFO]refreshToken 을 이용해 accessToken 재발급: {}", refreshToken);
        // refresh 토큰 만료여부 검사
        if (jwtService.isRefreshTokenExpired(refreshToken)) {
            log.info("refreshToken 만료: {}", refreshToken);
            throw new CustomException(AuthErrorCode.ACCESS_TOKEN_EXPIRED);
        }
        Long memberId = jwtService.getMemberIdFromRefreshToken(refreshToken);
        String redisRefreshToken = redisAuthService.getRefreshToken(memberId);
        // 유효한 jwt 토큰이라면, 사용자의 refreshToken 과 대조한다.
        if (redisRefreshToken == null || !redisRefreshToken.equals(refreshToken)) {
            log.warn("[AUTH_WARN] 사용자의 refreshToken 이 아님: {}", refreshToken);
            throw new CustomException(AuthErrorCode.ACCESS_DENIED);
        }
        String newAccessToken = jwtService.createAccessToken(memberId);
        return authMapper.toRefreshResponse(newAccessToken);
    }


    @Transactional
    public AllowanceResponse updateAllowance(String allowanceToken) {
        if (jwtService.isAllowanceTokenExpired(allowanceToken)) {
            log.info("약관 동의 토큰 만료: {}", allowanceToken);
            throw new CustomException(AuthErrorCode.TERMS_TOKEN_EXPIRED);
        }
        Member member = jwtService.getMemberFromAllowanceToken(allowanceToken);
        member.updateAllowance();
        memberRepository.save(member);
        Long memberId = member.getId();
        String accessToken = jwtService.createAccessToken(memberId);
        String refreshToken = jwtService.createRefreshToken(memberId);
        return authMapper.toAllowanceResponse(accessToken, refreshToken);
    }

    public Map<String, String> handleKakaoLogin(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("No Bearer token provided");
        }

        String kakaoAccessToken = authorizationHeader.substring(7); // 'Bearer ' 제거

        // 카카오 사용자 정보 요청
        Map<String, Object> kakaoUserInfo = fetchKakaoUserInfo(kakaoAccessToken);
        if (kakaoUserInfo == null || !kakaoUserInfo.containsKey("id")) {
            throw new IllegalArgumentException("카카오 사용자 정보가 없습니다.");
        }

        // 사용자 정보 추출
        Long kakaoId = ((Number) kakaoUserInfo.get("id")).longValue();
        String email = (String) ((Map<String, Object>) kakaoUserInfo.get("kakao_account")).get("email");
        String nickname = (String) ((Map<String, Object>) kakaoUserInfo.get("properties")).get("nickname");
        String profileImage = (String) ((Map<String, Object>) kakaoUserInfo.get("properties")).get("profile_image");

        // 사용자 식별 및 생성 로직
        Member member = findOrCreateMember(email, nickname, profileImage);

        // JWT 토큰 생성
        String accessToken = jwtService.createAccessToken(member.getId());
        String refreshToken = jwtService.createRefreshToken(member.getId());

        // 응답 반환
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    private Map<String, Object> fetchKakaoUserInfo(String kakaoAccessToken) {
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
            return kakaoResponse.getBody();
        } else {
            throw new RuntimeException("카카오 사용자 정보 조회 실패");
        }
    }

    private Member findOrCreateMember(String email, String nickname, String profileImage) {
        return memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    // 새 회원 생성
                    Member newMember = Member.builder()
                            .nickname(nickname)
                            .email(email)
                            .profileImage(profileImage)
                            .role(MemberRole.USER)
                            .allowance(false) // 기본값으로 약관 동의 미완료 상태
                            .build();
                    return memberRepository.save(newMember);
                });
    }
}
