package com.CPGroupH.domains.place.repository;

import com.CPGroupH.domains.place.entity.Place;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findTop5ByOrderByLikesDesc();
}
