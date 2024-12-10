package com.CPGroupH.domains.place.repository;

import com.CPGroupH.domains.place.entity.Place;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query("SELECT p FROM Place p " +
            "JOIN Recommendation r ON r.place = p " +
            "WHERE r.subCategory.id IN :subcategories")
    List<Place> findPlacesBySubcategories(@Param("subcategories") List<Long> subcategories);
    List<Place> findTop5ByOrderByLikesDesc();
}
