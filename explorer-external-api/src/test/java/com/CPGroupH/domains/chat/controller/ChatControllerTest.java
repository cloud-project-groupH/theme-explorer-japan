package com.CPGroupH.domains.chat.controller;

import com.CPGroupH.domains.chat.controller.utils.JwtTestUtil;
import com.CPGroupH.domains.chat.dto.request.MessageReqDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerTest {

    @LocalServerPort
    private int port;

    @Value("${jwt.access.secret}")
    String secretKey;

    @Test
    public void testWebSocketConnection() throws Exception {
        // WebSocket STOMP 클라이언트 생성
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String mockJwtToken = JwtTestUtil.createMockJwt(secretKey);
        String jwtToken = "Bearer " + mockJwtToken;

        WebSocketHttpHeaders httpHeaders = new WebSocketHttpHeaders();
        httpHeaders.add("Authorization", jwtToken);

        StompHeaders connectHeaders = new StompHeaders();
        connectHeaders.add("Authorization", jwtToken);

        // 서버 WebSocket URL
        String url = "ws://localhost:" + port + "/ws";

        // WebSocket 연결
        StompSession session = stompClient.connect(url, httpHeaders, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                log.info("Connected Websocket (Test)");
            }
        }, connectHeaders).get();
        assertNotNull(session, "STOMP session should be created");

        session.subscribe("/topic/chatroom/123", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return MessageReqDTO.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                log.info("Received: " + payload);
            }
        });

        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 객체 생성
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("sender", "TestUser");
        jsonObject.put("content", "Hello!");
        jsonObject.put("createdAt", "1");

        // 메시지 전송
        session.send("/app/chat.sendMessage/123", jsonObject);

        Thread.sleep(1000); // 메시지 수신 대기
    }
}

