package com.CPGroupH.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceStatus {
    LIKE("갈거에요"),
    WENT("갔다왔어요"),
    AGAIN("또 갈거에요");

    private final String description;
}
