package com.CPGroupH.domains.auth.security.service;

import com.CPGroupH.domains.member.repository.MemberRepository;
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


}
