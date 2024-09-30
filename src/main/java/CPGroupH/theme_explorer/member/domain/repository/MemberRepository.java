package CPGroupH.theme_explorer.member.domain.repository;

import CPGroupH.theme_explorer.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
