package com.wanted.fundfolio.domain.budget.repo;


import com.wanted.fundfolio.domain.budget.entity.BudgetCategory;
import com.wanted.fundfolio.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetCategoryRepositoryCustom {
    Long findAmountAll();

    Long findAmountByCategory(Category category);
}
