package com.wanted.fundfolio.api.category.service;

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
    public void save(Category category){
        categoryRepository.save(category);
    }
}
