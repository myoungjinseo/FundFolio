package com.wanted.fundfolio.api.category.service;

import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.category.entity.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    public List<CategoryType> getCategoryAll(){
        return Category.categoryList();
    }
}
