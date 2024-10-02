package CPGroupH.domains.member.entity;

import CPGroupH.domains.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @PersistenceContext
    private Long id;

    private String email;

    private String profileUrl;


}

