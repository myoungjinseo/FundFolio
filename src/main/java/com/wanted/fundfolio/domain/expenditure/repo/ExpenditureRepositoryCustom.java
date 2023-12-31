package com.wanted.fundfolio.domain.expenditure.repo;


import com.wanted.fundfolio.api.expenditure.dto.ExpenditureReadRequest;
import com.wanted.fundfolio.api.expenditure.dto.ExpenditureReadResponse;
import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.category.entity.CategoryType;
import com.wanted.fundfolio.domain.member.entity.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenditureRepositoryCustom {
    List<ExpenditureReadResponse> findList(ExpenditureReadRequest request, Member member, CategoryType categoryType);
    Long findAmountAll(Member member);
    Optional<Long> findAmountByCategory(Category category, Member member);
    Optional<Long> findTodayAmount( Member member,LocalDate date);
    Optional<Long> findTodayAmountByCategory(Category category, Member member,LocalDate date);
    Optional<Long> findComparedAmountAll(Member member, LocalDate startDate, LocalDate lastDate);
    Optional<Long> findComparedAmountByCategory(Category category, Member member, LocalDate startDate, LocalDate lastDate);

    Optional<Long> findTodayTotalAmount(Member member, LocalDate date);
    Optional<Long> findTodayTotalAmountByCategory(Member member,Category category,LocalDate date);

}
