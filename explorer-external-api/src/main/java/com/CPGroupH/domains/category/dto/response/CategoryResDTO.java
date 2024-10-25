package com.CPGroupH.domains.category.dto.response;

import com.CPGroupH.domains.category.entity.Category;

public record CategoryResDTO(Long id, String title, String description) {

    public static CategoryResDTO fromEntity(Category category) {
        return new CategoryResDTO(
                category.getId(),
                category.getTitle(),
                category.getDescription()
        );
    }

    public Category toEntity() {
        return Category.builder()
                .title(this.title)
                .description(this.description)
                .build();
    }
}
