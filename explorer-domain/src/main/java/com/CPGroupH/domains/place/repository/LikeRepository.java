package com.CPGroupH.domains.place.repository;

import com.CPGroupH.domains.place.entity.Like;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByMemberId(Long memberId);
}
