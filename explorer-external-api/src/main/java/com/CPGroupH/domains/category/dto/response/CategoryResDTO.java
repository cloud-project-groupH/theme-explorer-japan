package com.CPGroupH.domains.category.dto.response;

import com.CPGroupH.domains.category.entity.Category;

public record CategoryResDTO(Long id, String title) {

    public static CategoryResDTO fromEntity(Category category) {
        return new CategoryResDTO(
                category.getId(),
                category.getTitle()
        );
    }

    public Category toEntity() {
        return Category.builder()
                .title(this.title)
                .build();
    }
}
