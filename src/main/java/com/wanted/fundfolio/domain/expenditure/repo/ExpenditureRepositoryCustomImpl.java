package com.wanted.fundfolio.domain.expenditure.repo;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.fundfolio.api.expenditure.dto.ExpenditureReadRequest;
import com.wanted.fundfolio.api.expenditure.dto.ExpenditureReadResponse;
import com.wanted.fundfolio.domain.category.entity.Category;
import com.wanted.fundfolio.domain.category.entity.CategoryType;
import com.wanted.fundfolio.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.wanted.fundfolio.domain.category.entity.QCategory.category;
import static com.wanted.fundfolio.domain.expenditure.entity.QExpenditure.expenditure;

@Repository
@RequiredArgsConstructor
public class ExpenditureRepositoryCustomImpl implements ExpenditureRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ExpenditureReadResponse> findList(ExpenditureReadRequest request, Member member, CategoryType categoryType){
        return jpaQueryFactory
                .select(Projections.fields(ExpenditureReadResponse.class,expenditure.id.as("ExpenditureId")
                        ,expenditure.amount,expenditure.date,category.categoryType.as("categoryType"),expenditure.excludeTotal))
                .from(expenditure)
                .where(expenditure.date.between(request.getStartDate(),request.getEndDate()),
                        category.categoryType.eq(categoryType),
                        expenditure.amount.between(request.getMinAmount(), request.getMaxAmount()),
                        expenditure.member.eq(member))
                .leftJoin(category)
                .on(expenditure.category.eq(category))
                .fetch();
    }

    @Override
    public Long findAmountAll(Member member){
        return jpaQueryFactory
                .select(expenditure.amount.sum())
                .from(expenditure)
                .where(
                        expenditure.member.eq(member),
                        expenditure.excludeTotal)
                .fetchOne();
    }

    @Override
    public Optional<Long> findAmountByCategory(Category category, Member member){
        return Optional.ofNullable(jpaQueryFactory
                .select(expenditure.amount.sum())
                .from(expenditure)
                .where(expenditure.category.eq(category),
                        expenditure.excludeTotal,
                        expenditure.member.eq(member))
                .fetchOne());
    }
}
