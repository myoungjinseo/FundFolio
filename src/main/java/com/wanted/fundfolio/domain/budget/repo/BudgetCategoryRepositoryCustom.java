package com.wanted.fundfolio.domain.budget.repo;


import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.member.entity.Member;

public interface BudgetCategoryRepositoryCustom {
    Long findAmountAll();

    Long findAmountByCategory(Category category);
    Long findAmountAllByMember(Member member);
    Long findAmountByCategoryMember(Member member,Category category);
}
