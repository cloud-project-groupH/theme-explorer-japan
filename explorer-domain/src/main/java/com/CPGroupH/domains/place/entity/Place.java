package com.CPGroupH.domains.place.entity;

import com.CPGroupH.domains.address.entity.Address;
import com.CPGroupH.domains.chat.entity.ChatRoomPlace;
import com.CPGroupH.domains.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Column
    private String description;

    @Column(nullable = false)
    private Integer likes;

    @Column(nullable = false)
    private Integer visited;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    //S3
    @Column
    private String imageKey;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> recommendations = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomPlace> chatRoomPlaces = new ArrayList<>();


    @Builder
    public Place(Address address, String title, String description, Integer likes, Integer visited, String latitude, String longitude, String imageKey) {
        this.address = address;
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.visited = visited;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageKey = imageKey;
    }

    public void increaseLikes(Place place) {
        place.likes++;
    }

    public void decreaseLikes(Place place) {
        place.likes--;
    }

    public void increaseVisited(Place place) {
        place.visited++;
    }

    public void decreaseVisited(Place place) {
        place.visited--;
    }
}
