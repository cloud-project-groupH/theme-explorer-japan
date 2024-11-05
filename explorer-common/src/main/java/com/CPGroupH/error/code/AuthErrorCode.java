package com.CPGroupH.error.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    //
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH-001", "사용자 인증에 실패하였습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTH-002", "접근이 거부되었습니다. 이 리소스에 대한 권한이 없습니다."),
    INSUFFICIENT_PERMISSIONS(HttpStatus.FORBIDDEN, "AUTH-003", "작업을 수행할 권한이 부족합니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "AUTH-004", "로그인에 실패했습니다."),

    //토큰 관련 에러
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-005", "엑세스 토큰의 유효기간이 만료되었습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-005", "리프레시 토큰의 유효기간이 만료되었습니다."),
    TERMS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-006", "약관 동의 토큰의 유효기간이 만료되었습니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.BAD_REQUEST, "AUTH-007", "잘못된 토큰 형식입니다."),
    INVALID_TOKEN_SIGNATURE(HttpStatus.BAD_REQUEST, "AUTH-008", "토큰의 서명이 일치하지 않습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "AUTH-009", "토큰의 특정 헤더나 클레임이 지원되지 않습니다."),

    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH-010", "쿠키에 리프레시 토큰이 없습니다."),
    ACCESS_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH-011", "요청 헤더에 엑세스 토큰이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}