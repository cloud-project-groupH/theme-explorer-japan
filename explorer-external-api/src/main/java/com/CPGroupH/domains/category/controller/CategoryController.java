package com.CPGroupH.domains.category.controller;

import com.CPGroupH.domains.category.dto.response.CategoryResDTO;
import com.CPGroupH.domains.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@Tag(name = "Category", description = "Category API")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "모든 카테고리 가져오기")
    @GetMapping("/")
    public ResponseEntity<List<CategoryResDTO>> getAllCategory(
    ) {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }
}
