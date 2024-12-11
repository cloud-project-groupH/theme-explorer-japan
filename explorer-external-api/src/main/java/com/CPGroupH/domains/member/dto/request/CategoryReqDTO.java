package com.CPGroupH.domains.member.dto.request;

import java.util.List;

public record CategoryReqDTO(
        List<Long> categoryIds
) {
}
