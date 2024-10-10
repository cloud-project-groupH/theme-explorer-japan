package com.CPGroupH.domains.member.entity;

import com.CPGroupH.common.enums.MemberRole;
import com.CPGroupH.domains.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    private String profileUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberRole role;

    @Column
    private LocalDateTime deletedAt;

    @Builder
    public Member(String email, String profileUrl, MemberRole role) {
        this.email = email;
        this.profileUrl = profileUrl;
        this.role = role;
    }

    @PreRemove
    public void onDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}

