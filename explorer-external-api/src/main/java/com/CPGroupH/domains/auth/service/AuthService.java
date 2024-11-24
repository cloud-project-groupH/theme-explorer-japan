package com.CPGroupH.domains.auth.security.service;

import com.CPGroupH.domains.auth.dto.response.AllowanceResponse;
import com.CPGroupH.domains.auth.dto.response.RefreshResponse;
import com.CPGroupH.domains.auth.mapper.AuthMapper;
import com.CPGroupH.domains.member.entity.Member;
import com.CPGroupH.domains.member.repository.MemberRepository;
import com.CPGroupH.error.code.AuthErrorCode;
import com.CPGroupH.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final RedisAuthService redisAuthService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final AuthMapper authMapper;

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
}
