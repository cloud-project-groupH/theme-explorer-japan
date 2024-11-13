package com.CPGroupH.domains.category.service.impl;

import com.CPGroupH.domains.category.dto.response.CategoryResDTO;
import com.CPGroupH.domains.category.entity.Category;
import com.CPGroupH.domains.category.repository.CategoryRepository;
import com.CPGroupH.domains.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResDTO> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(CategoryResDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
