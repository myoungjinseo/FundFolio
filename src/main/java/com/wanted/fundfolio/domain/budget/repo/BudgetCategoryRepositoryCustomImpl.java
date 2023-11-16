package com.wanted.fundfolio.domain.budget.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.wanted.fundfolio.domain.budget.entity.QBudgetCategory.budgetCategory;
import static com.wanted.fundfolio.domain.budget.entity.QBudget.budget;

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

    @Override
    public Long findAmountAllByMember(Member member){
        return jpaQueryFactory
                .select(budgetCategory.amount.sum())
                .from(budgetCategory)
                .join(budget)
                .on(budgetCategory.budget.eq(budget))
                .where(budget.member.eq(member))
                .fetchOne();
    }

    @Override
    public Long findAmountByCategoryMember(Member member,Category category){
        return jpaQueryFactory
                .select(budgetCategory.amount.sum())
                .from(budgetCategory)
                .where(budgetCategory.category.eq(category),
                        budget.member.eq(member))
                .join(budget)
                .on(budgetCategory.budget.eq(budget))
                .fetchOne();
    }
}
