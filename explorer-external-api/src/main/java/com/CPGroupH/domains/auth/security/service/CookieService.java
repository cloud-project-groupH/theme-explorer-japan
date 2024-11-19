package com.CPGroupH.domains.auth.security.service;

import com.CPGroupH.error.code.AuthErrorCode;
import com.CPGroupH.error.exception.CustomException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    @Value("${cookie.max-age}")
    private int maxAge;

    @Value("${cookie.domain}")
    private String domain;

    @Value("${cookie.access-name}")
    private String accessName;

    @Value("${cookie.refresh-name}")
    private String refreshName;

    private Cookie createTokenCookie(String cookieName, String cookie) {
        Cookie newCookie = new Cookie(accessName, cookie);
        newCookie.setMaxAge(maxAge);
        newCookie.setDomain(domain);
        newCookie.setPath("/");
        newCookie.setHttpOnly(false);
        newCookie.setSecure(true);
        return newCookie;
    }

    private void clearTokenCookie(String cookieName, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    public Cookie createAccessTokenCookie(String token) {
        return createTokenCookie(accessName, token);
    }

    public Cookie createRefreshTokenCookie(String refreshToken) {
        return createTokenCookie(refreshName, refreshToken);
    }

    public void clearAccessAndRefreshCookie(HttpServletResponse response) {
        clearTokenCookie(accessName, response);
        clearTokenCookie(refreshName, response);
    }

    public String getRefreshTokenCookie(HttpServletRequest request) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            throw new CustomException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        for (Cookie cookie : cookies) {
            if (cookie != null && cookie.getName().equals(refreshName)) {
                refreshToken = cookie.getValue();
            }
        }
        if (refreshToken == null) {
            throw new CustomException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        return refreshToken;
    }
}
