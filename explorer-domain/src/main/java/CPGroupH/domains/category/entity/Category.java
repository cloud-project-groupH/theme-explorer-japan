package CPGroupH.domains.category.entity;

import CPGroupH.domains.common.entity.BaseEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class Category extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
}
