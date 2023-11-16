package com.wanted.fundfolio.api.expenditure.dto;

import com.wanted.fundfolio.domain.category.entity.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ExpenditureRecommendResponse {
    private LocalDate today;
    private List<ExpenditureAmountByCategoryResponse> list;
    private long amount;


    public static ExpenditureRecommendResponse of(List<ExpenditureAmountByCategoryResponse> list, long amount) {
        return new ExpenditureRecommendResponse(
                LocalDate.now(),
                list,
                amount
        );
    }
}
