package com.CPGroupH.domains.category.repository;

import com.CPGroupH.domains.category.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
