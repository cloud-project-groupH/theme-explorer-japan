package com.CPGroupH.domains.auth.security.service;

import com.CPGroupH.domains.auth.enums.AuthToken;
import com.CPGroupH.domains.member.entity.Member;
import com.CPGroupH.domains.member.repository.MemberRepository;
import com.CPGroupH.error.code.AuthErrorCode;
import com.CPGroupH.error.code.CommonErrorCode;
import com.CPGroupH.error.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final RedisAuthService redisAuthService;
    private final MemberRepository memberRepository;

    @Value("${jwt.access.secret}")
    private String accessTokenSecret;

    @Value("${jwt.access.expires-in}")
    private long accessTokenExpiresIn;

    @Value("${jwt.refresh.secret}")
    private String refreshTokenSecret;

    @Value("${jwt.refresh.expires-in}")
    private long refreshTokenExpiresIn;

    @Value("${jwt.allowance.secret}")
    private String allowanceSecret;

    @Value("${jwt.allowance.expires-in}")
    private long allowanceTokenExpiresIn;

    private SecretKey accessTokenSecretKey;
    private SecretKey refreshTokenSecretKey;
    private SecretKey allowanceTokenSecretKey;

    @PostConstruct
    public void init() {
        accessTokenSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenSecret));
        refreshTokenSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshTokenSecret));
        allowanceTokenSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(allowanceSecret));
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        throw new CustomException(AuthErrorCode.ACCESS_TOKEN_NOT_FOUND);
    }

    public String createAccessToken(Long memberId) {
        return createToken(memberId, accessTokenExpiresIn, accessTokenSecretKey);
    }

    public String createRefreshToken(Long memberId) {
        String refreshToken = createToken(memberId, refreshTokenExpiresIn, refreshTokenSecretKey);
        redisAuthService.setRefreshToken(memberId, refreshToken, refreshTokenExpiresIn);
        return refreshToken;
    }

    public String createAllowanceToken(Long memberId) {
        return createToken(memberId, allowanceTokenExpiresIn, allowanceTokenSecretKey);
    }

    public boolean isAllowanceTokenExpired(String allowanceToken) {
        return isTokenExpired(allowanceToken, allowanceTokenSecretKey, AuthToken.TERMS);
    }

    public boolean isRefreshTokenExpired(String refreshToken) {
        return isTokenExpired(refreshToken, refreshTokenSecretKey, AuthToken.REFRESH_TOKEN);
    }

    public boolean isAccessTokenExpired(String accessToken) {
        return isTokenExpired(accessToken, accessTokenSecretKey, AuthToken.ACCESS_TOKEN);
    }

    public Member getMemberFromAccessToken(String accessToken) {
        Long memberId = getMemberIdFromAccessToken(accessToken);
       return memberRepository.findById(memberId).orElseThrow(() -> {
            return new CustomException(AuthErrorCode.USER_NOT_EXIST);
        });
    }

    public Member getMemberFromRefreshToken(String refreshToken) {
        Long memberId = getMemberIdFromRefreshToken(refreshToken);
        return memberRepository.findById(memberId).orElseThrow(() -> {
            return new CustomException(AuthErrorCode.USER_NOT_EXIST);
        });
    }

    public Member getMemberFromAllowanceToken(String allowanceToken) {
        Long memberId = getMemberIdFromAllowanceToken(allowanceToken);
        return memberRepository.findById(memberId).orElseThrow(() -> {
            return new CustomException(AuthErrorCode.USER_NOT_EXIST);
        });
    }

    public Long getMemberIdFromRefreshToken(String refreshToken) {
        return parseMemberId(refreshToken, refreshTokenSecretKey);
    }

    public Long getMemberIdFromAccessToken(String accessToken) {
        return parseMemberId(accessToken, accessTokenSecretKey);
    }

    public Long getMemberIdFromAllowanceToken(String allowanceToken) {
        return parseMemberId(allowanceToken, allowanceTokenSecretKey);
    }


    private Claims getClaimsFromToken(String token, SecretKey secretKey) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String createToken(Long memberId, long expiresIn, SecretKey secretKey) {
        return Jwts.builder()
                .claim("memberId", memberId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ expiresIn*1000 ))
                .signWith(secretKey, SIG.HS256)
                .compact();
    }

    private Long parseMemberId(String token, SecretKey secretKey) {
        return getClaimsFromToken(token, secretKey).get("memberId", Long.class);
    }

    private boolean isTokenExpired(String token, SecretKey secretKey, AuthToken authToken) {
        try{
            return getClaimsFromToken(token, secretKey).getExpiration().before(new Date());
        }catch (JwtException e) {
            if(e instanceof ExpiredJwtException) {
                switch (authToken){
                    case ACCESS_TOKEN -> throw new CustomException(AuthErrorCode.ACCESS_TOKEN_EXPIRED);
                    case REFRESH_TOKEN -> throw new CustomException(AuthErrorCode.REFRESH_TOKEN_EXPIRED);
                    case TERMS -> throw new CustomException(AuthErrorCode.TERMS_TOKEN_EXPIRED);
                }
            }
            if(e instanceof MalformedJwtException) {
                throw new CustomException(AuthErrorCode.INVALID_TOKEN_FORMAT);
            }
            else if(e instanceof SignatureException) {
                throw new CustomException(AuthErrorCode.INVALID_TOKEN_SIGNATURE);
            }
            else if(e instanceof UnsupportedJwtException){
                throw new CustomException(AuthErrorCode.UNSUPPORTED_TOKEN);
            }
            else{//토큰 검사 중 알 수 없는 오류 발생시
                throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR);
            }
        }

    }
}
