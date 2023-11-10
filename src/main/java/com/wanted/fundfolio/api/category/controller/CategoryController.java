package com.wanted.fundfolio.api.category.controller;

import com.wanted.fundfolio.api.category.service.CategoryService;
import com.wanted.fundfolio.domain.category.entity.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;
    @GetMapping("/categoies/all")
    public ResponseEntity<?> getCategoryAll(){
        List<CategoryType> list = categoryService.getCategoryAll();
        return ResponseEntity.ok().body(list);
    }
}
