package com.CPGroupH.config;

import com.CPGroupH.domains.auth.security.service.JwtService;
import com.CPGroupH.domains.chat.security.JwtHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtService jwtService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // 메시지를 브로드캐스트할 경로 (구독용)
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 보낼 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // TODO: 실제 배포 시  특정 도메인만 가능하게 수정해야함
        registry.addEndpoint("/ws").setAllowedOrigins("*").addInterceptors(new JwtHandshakeInterceptor(jwtService));
        // .withSockJS();
    }
}
