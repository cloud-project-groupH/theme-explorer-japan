package com.CPGroupH.domains.auth.dto.response;

import lombok.Builder;

@Builder
public record AllowanceResponse(
        String accessToken,
        String refreshToken
) {
}