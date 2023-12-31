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

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static com.wanted.fundfolio.domain.category.entity.QCategory.category;
import static com.wanted.fundfolio.domain.expenditure.entity.QExpenditure.expenditure;

@Repository
@RequiredArgsConstructor
public class ExpenditureRepositoryCustomImpl implements ExpenditureRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ExpenditureReadResponse> findList(ExpenditureReadRequest request, Member member, CategoryType categoryType) {
        return jpaQueryFactory
                .select(Projections.fields(ExpenditureReadResponse.class, expenditure.id.as("ExpenditureId")
                        , expenditure.amount, expenditure.date, category.categoryType.as("categoryType"), expenditure.excludeTotal))
                .from(expenditure)
                .where(expenditure.date.between(request.getStartDate(), request.getEndDate()),
                        category.categoryType.eq(categoryType),
                        expenditure.amount.between(request.getMinAmount(), request.getMaxAmount()),
                        expenditure.member.eq(member))
                .leftJoin(category)
                .on(expenditure.category.eq(category))
                .fetch();
    }

    @Override
    public Long findAmountAll(Member member) {
        return jpaQueryFactory
                .select(expenditure.amount.sum())
                .from(expenditure)
                .where(
                        expenditure.member.eq(member),
                        expenditure.excludeTotal)
                .fetchOne();
    }

    @Override
    public Optional<Long> findAmountByCategory(Category category, Member member) {
        return Optional.ofNullable(jpaQueryFactory
                .select(expenditure.amount.sum())
                .from(expenditure)
                .where(expenditure.category.eq(category),
                        expenditure.excludeTotal,
                        expenditure.member.eq(member))
                .fetchOne());
    }

    @Override
    public Optional<Long> findTodayAmount(Member member, LocalDate date) {
        return Optional.ofNullable(jpaQueryFactory
                .select(expenditure.amount.sum())
                .from(expenditure)
                .where(expenditure.excludeTotal,
                        expenditure.member.eq(member),
                        expenditure.date.eq(date))
                .fetchOne());
    }

    @Override
    public Optional<Long> findTodayAmountByCategory(Category category, Member member, LocalDate date) {
        return Optional.ofNullable(jpaQueryFactory
                .select(expenditure.amount.sum())
                .from(expenditure)
                .where(expenditure.category.eq(category),
                        expenditure.excludeTotal,
                        expenditure.member.eq(member),
                        expenditure.date.eq(date))
                .fetchOne());
    }

    @Override
    public Optional<Long> findComparedAmountAll(Member member, LocalDate startDate, LocalDate lastDate) {
        return Optional.ofNullable(jpaQueryFactory
                .select(expenditure.amount.sum())
                .from(expenditure)
                .where(expenditure.excludeTotal,
                        expenditure.member.eq(member),
                        expenditure.date.between(startDate, lastDate))
                .fetchOne());
    }

    @Override
    public Optional<Long> findComparedAmountByCategory(Category category, Member member, LocalDate startDate, LocalDate lastDate) {
        return Optional.ofNullable(jpaQueryFactory
                .select(expenditure.amount.sum())
                .from(expenditure)
                .where(expenditure.category.eq(category),
                        expenditure.excludeTotal,
                        expenditure.member.eq(member),
                        expenditure.date.between(startDate, lastDate))
                .fetchOne());
    }

    @Override
    public Optional<Long> findTodayTotalAmount(Member member, LocalDate date) {
        long a = Optional.ofNullable(jpaQueryFactory
                .select(expenditure.amount.sum())
                .from(expenditure)
                .where(expenditure.excludeTotal,
                        expenditure.member.eq(member),
                        expenditure.date.between(YearMonth.now().atDay(1),date))
                .fetchOne()).orElse(0L);
        Long b = Optional.ofNullable(jpaQueryFactory
                .select(expenditure.amount.sum())
                .from(expenditure)
                .where(expenditure.excludeTotal,
                        expenditure.date.between(YearMonth.now().atDay(1),date))
                .fetchOne()).orElse(0L);
        return Optional.ofNullable( b.longValue()/ a);

    }

    @Override
    public Optional<Long> findTodayTotalAmountByCategory(Member member, Category category, LocalDate date){
        long a = Optional.ofNullable(jpaQueryFactory
                .select(expenditure.amount.sum())
                .from(expenditure)
                .where(expenditure.category.eq(category),
                        expenditure.excludeTotal,
                        expenditure.member.eq(member),
                        expenditure.date.between(YearMonth.now().atDay(1),date))
                .fetchOne()).orElse(0l);
        Long b = Optional.ofNullable(jpaQueryFactory
                .select(expenditure.amount.sum())
                .from(expenditure)
                .where(expenditure.category.eq(category),
                        expenditure.excludeTotal,
                        expenditure.date.between(YearMonth.now().atDay(1),date))
                .fetchOne()).orElse(0L);
        return Optional.ofNullable( b.longValue()/ a);
    }


}
