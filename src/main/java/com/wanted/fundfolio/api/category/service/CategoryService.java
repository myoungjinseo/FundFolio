package com.wanted.fundfolio.api.category.service;

import com.wanted.fundfolio.api.budget.dto.BudgetRequest;
import com.wanted.fundfolio.domain.budget.entity.BudgetCategory;
import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.category.entity.CategoryType;
import com.wanted.fundfolio.domain.category.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public List<CategoryType> getCategoryAll(){
        return Category.categoryList();
    }

    @Transactional
    public Category save(CategoryType categoryType){
        Category category = categoryRepository.findByCategoryType(categoryType);
        if(category == null){
            category = Category.builder()
                    .categoryType(categoryType)
                    .build();
            categoryRepository.save(category);
        }
        return category;
    }

    public List<Category> categoryList(){
        return categoryRepository.findAll();
    }
}
