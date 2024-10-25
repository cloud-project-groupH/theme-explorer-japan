package com.CPGroupH.domains.place.dto.response;

import com.CPGroupH.domains.address.entity.Address;
import com.CPGroupH.domains.place.entity.Place;

//파일 위치만 정한 것, 수정 필요
public record PlaceResDTO(Long id, Address address, String title, String latitude, String longitude) {
    public static PlaceResDTO fromEntity(Place place) {
        return new PlaceResDTO(
                place.getId(),
                place.getAddress(),
                place.getTitle(),
                place.getLatitude(),
                place.getLongitude()
        );
    }

    public Place toEntity() {
        return Place.builder()
                .address(this.address)
                .title(this.title)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .build();
    }
}
