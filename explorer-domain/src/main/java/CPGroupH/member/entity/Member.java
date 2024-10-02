package CPGroupH.member.entity;

import CPGroupH.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;

@Entity
public class Member extends BaseEntity {
    @Id
    @PersistenceContext
    private Long id;
}

