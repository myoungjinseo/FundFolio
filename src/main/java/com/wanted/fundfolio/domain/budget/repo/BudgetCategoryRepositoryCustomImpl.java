package com.wanted.fundfolio.domain.budget.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.fundfolio.domain.category.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.wanted.fundfolio.domain.budget.entity.QBudgetCategory.budgetCategory;

@Repository
@RequiredArgsConstructor
public class BudgetCategoryRepositoryCustomImpl implements BudgetCategoryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findAmountAll(){
        return jpaQueryFactory
                .select(budgetCategory.amount.sum())
                .from(budgetCategory)
                .fetchOne();
    }

    @Override
    public Long findAmountByCategory(Category category){
        return jpaQueryFactory
                .select(budgetCategory.amount.sum())
                .from(budgetCategory)
                .where(budgetCategory.category.eq(category))
                .fetchOne();
    }
}
