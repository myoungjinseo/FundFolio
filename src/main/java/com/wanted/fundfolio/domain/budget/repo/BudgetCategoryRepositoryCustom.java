package com.wanted.fundfolio.domain.budget.repo;


import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.member.entity.Member;

import java.time.LocalDate;
import java.util.Optional;

public interface BudgetCategoryRepositoryCustom {
    Long findAmountAll();

    Long findAmountByCategory(Category category);
    Long findAmountAllByMember(Member member);
    Long findAmountByCategoryMember(Member member,Category category);

    Optional<Long> findTodayTotalAmount(Member member, LocalDate date);
    Optional<Long> findTodayTotalAmountByCategory(Member member,Category category,LocalDate date);
}
