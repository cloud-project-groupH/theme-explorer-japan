package com.CPGroupH.domains.auth.security.oauth2.handler;

import com.CPGroupH.domains.auth.security.service.CookieService;
import com.CPGroupH.domains.auth.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final CookieService cookieService;
}
