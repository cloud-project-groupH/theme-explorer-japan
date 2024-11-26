package com.CPGroupH.domains.member.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record MemberLikeResDTO(
        List<Long> placeIds
) {
}
