package CPGroupH.domains.place.repository;

import CPGroupH.domains.place.entity.Togo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TogoRepository extends JpaRepository<Togo, Long> {
}
