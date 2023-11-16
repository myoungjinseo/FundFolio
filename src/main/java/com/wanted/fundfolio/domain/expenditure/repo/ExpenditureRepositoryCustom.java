package com.wanted.fundfolio.domain.expenditure.repo;


import com.wanted.fundfolio.api.expenditure.dto.ExpenditureReadRequest;
import com.wanted.fundfolio.api.expenditure.dto.ExpenditureReadResponse;
import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.category.entity.CategoryType;
import com.wanted.fundfolio.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface ExpenditureRepositoryCustom {
    List<ExpenditureReadResponse> findList(ExpenditureReadRequest request, Member member, CategoryType categoryType);
    Long findAmountAll(Member member);
    Optional<Long> findAmountByCategory(Category category, Member member);
}
