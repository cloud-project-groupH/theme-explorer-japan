package com.CPGroupH.domains.address.dto.response;

import com.CPGroupH.domains.address.entity.Address;

public record AddressResDTO(Long id, String city, String district, String street) {
    public static AddressResDTO fromEntity(Address address) {
        return new AddressResDTO(
                address.getId(),
                address.getCity(),
                address.getDistrict(),
                address.getStreet()
        );
    }

    public Address toEntity() {
        return Address.builder()
                .city(this.city)
                .district(this.district)
                .street(this.street)
                .build();
    }
}