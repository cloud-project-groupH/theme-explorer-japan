package com.CPGroupH.domains.place.repository;

import com.CPGroupH.domains.place.entity.Visited;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitedRepository extends JpaRepository<Visited, Long> {
    List<Visited> findByMemberId(Long memberId);
}
