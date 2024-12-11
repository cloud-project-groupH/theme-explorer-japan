package com.CPGroupH.domains.member.entity;

import com.CPGroupH.common.enums.MemberRole;
import com.CPGroupH.domains.category.entity.Preference;
import com.CPGroupH.domains.category.entity.SubCategory;
import com.CPGroupH.domains.common.entity.BaseEntity;
import com.CPGroupH.domains.place.entity.Like;
import com.CPGroupH.domains.place.entity.Visited;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false, length = 255)
    private String email;

    private String profileImage;

    @Column(nullable = false)
    private Boolean allowance = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberRole role;

    @Column
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visited> visited = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Preference> preferences = new ArrayList<>();

    @Builder
    public Member(String nickname, String email, String profileImage, Boolean allowance, MemberRole role) {
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.allowance = allowance;
        this.role = role;
    }

    @PreRemove
    public void onDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void updateAllowance() {
        this.allowance = true;
    }
    public void addPreferences(List<SubCategory> categories) {
        if (categories == null || categories.isEmpty()) {
            return;
        }
        for (SubCategory category : categories) {
            if (this.preferences.stream().noneMatch(pref -> pref.getCategory().equals(category))) {
                this.preferences.add(new Preference(this, category));
            }
        }
    }
}

