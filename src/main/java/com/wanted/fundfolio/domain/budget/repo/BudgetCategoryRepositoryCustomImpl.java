package com.wanted.fundfolio.domain.budget.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

import static com.wanted.fundfolio.domain.budget.entity.QBudgetCategory.budgetCategory;
import static com.wanted.fundfolio.domain.budget.entity.QBudget.budget;

@Repository
@RequiredArgsConstructor
public class BudgetCategoryRepositoryCustomImpl implements BudgetCategoryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findAmountAll() {
        return jpaQueryFactory
                .select(budgetCategory.amount.sum())
                .from(budgetCategory)
                .fetchOne();
    }

    @Override
    public Long findAmountByCategory(Category category) {
        return jpaQueryFactory
                .select(budgetCategory.amount.sum())
                .from(budgetCategory)
                .where(budgetCategory.category.eq(category))
                .fetchOne();
    }

    @Override
    public Long findAmountAllByMember(Member member) {
        return jpaQueryFactory
                .select(budgetCategory.amount.sum())
                .from(budgetCategory)
                .join(budget)
                .on(budgetCategory.budget.eq(budget))
                .where(budget.member.eq(member))
                .fetchOne();
    }

    @Override
    public Long findAmountByCategoryMember(Member member, Category category) {
        return jpaQueryFactory
                .select(budgetCategory.amount.sum())
                .from(budgetCategory)
                .where(budgetCategory.category.eq(category),
                        budget.member.eq(member))
                .join(budget)
                .on(budgetCategory.budget.eq(budget))
                .fetchOne();
    }

    @Override
    public Optional<Long> findTodayTotalAmount(Member member, LocalDate date) {
        long a = Optional.ofNullable(jpaQueryFactory
                .select(budgetCategory.amount.sum())
                .from(budgetCategory)
                .where(budget.member.eq(member),
                        budget.date.eq(date))
                .join(budget)
                .on(budgetCategory.budget.eq(budget))
                .fetchOne()).orElse(0L);
        Long b = Optional.ofNullable(jpaQueryFactory
                .select(budgetCategory.amount.sum())
                .from(budgetCategory)
                .where(budget.date.eq(date))
                .join(budget)
                .on(budgetCategory.budget.eq(budget))
                .fetchOne()).orElse(0L);

        return Optional.ofNullable( b.longValue()/a );

    }

    @Override
    public Optional<Long> findTodayTotalAmountByCategory(Member member, Category category, LocalDate date){
        long a = Optional.ofNullable(
                jpaQueryFactory
                        .select(budgetCategory.amount.sum())
                        .from(budgetCategory)
                        .where(budget.member.eq(member),
                                budgetCategory.category.eq(category),
                                budget.date.eq(date))
                        .join(budget)
                        .on(budgetCategory.budget.eq(budget))
                        .fetchOne()).orElse(0L);

        Long b = Optional.ofNullable(jpaQueryFactory
                .select(budgetCategory.amount.sum())
                .from(budgetCategory)
                .where(budget.date.eq(date),
                        budgetCategory.category.eq(category))
                .join(budget)
                .on(budgetCategory.budget.eq(budget))
                .fetchOne()).orElse(0L);


        return Optional.ofNullable( b.longValue()/a);
    }
}
