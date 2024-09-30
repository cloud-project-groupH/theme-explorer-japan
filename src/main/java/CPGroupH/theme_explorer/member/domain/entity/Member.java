package CPGroupH.theme_explorer.member.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;

@Entity
public class Member {
    @Id
    @PersistenceContext
    private Long id;
}
