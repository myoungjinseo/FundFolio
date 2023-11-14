package com.wanted.fundfolio.domain.budget.repo;


import com.wanted.fundfolio.domain.budget.entity.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory,Long> {
}
