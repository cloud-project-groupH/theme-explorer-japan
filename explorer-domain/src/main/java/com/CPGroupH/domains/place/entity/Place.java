package com.CPGroupH.domains.place.entity;

import com.CPGroupH.domains.address.entity.Address;
import com.CPGroupH.domains.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "places")
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer likes;

    @Column(nullable = false)
    private Integer visited;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Builder
    public Place(Address address, String title, Integer likes, Integer visited, String latitude, String longitude) {
        this.address = address;
        this.title = title;
        this.likes = likes;
        this.visited = visited;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void increaseLikes() {
        this.likes++;
    }
    public void decreaseLikes() {
        this.likes--;
    }

    public void increaseVisited() {
        this.visited++;
    }
    public void decreaseVisited() {
        this.visited--;
    }
}
