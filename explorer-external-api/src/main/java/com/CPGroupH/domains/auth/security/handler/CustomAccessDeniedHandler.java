package com.CPGroupH.domains.auth.security.handler;

import com.CPGroupH.error.code.AuthErrorCode;
import com.CPGroupH.response.ResponseFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {

        ResponseEntity<Object> errorResponse = ResponseFactory.failure(AuthErrorCode.ACCESS_DENIED);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(errorResponse.getStatusCode()
                .value());
        httpServletResponse.getWriter()
                .write(new ObjectMapper().writeValueAsString(errorResponse.getBody()));
        httpServletResponse.getWriter()
                .flush();
    }
}
