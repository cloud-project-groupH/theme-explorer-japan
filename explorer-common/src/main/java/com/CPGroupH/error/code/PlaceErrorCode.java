package com.CPGroupH.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PlaceErrorCode {

    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "PLACE-001", "장소가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
