package com.CPGroupH.domains.chat.security;

import com.CPGroupH.domains.auth.security.service.JwtService;
import com.CPGroupH.error.code.ErrorCode;
import com.CPGroupH.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // HTTP 헤더에서 JWT 토큰 추출
        String token = request.getHeaders().getFirst("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                jwtService.isAccessTokenExpired(token);
                return true;
            } catch (CustomException e) {
                ErrorCode errorCode = e.getErrorCode();

                System.out.println(errorCode);

                response.setStatusCode(errorCode.getHttpStatus());
                response.getHeaders().add("Error-Code", errorCode.getCode());
                response.getHeaders().add("Error-Message", errorCode.getMessage());
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 핸드셰이크 이후 추가 작업 필요 시 구현
    }
}