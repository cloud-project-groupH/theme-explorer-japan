package com.CPGroupH.domains.place.dto.response;

public record SearchPlaceResDTO(
    Long id,
    String title,
    String imageUrl,
    Long likes
) {
}
