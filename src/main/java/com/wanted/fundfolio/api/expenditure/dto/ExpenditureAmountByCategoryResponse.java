package com.wanted.fundfolio.api.expenditure.dto;

import com.wanted.fundfolio.domain.category.entity.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenditureAmountByCategoryResponse {
    private CategoryType categoryType;
    private long amount;

    public static ExpenditureAmountByCategoryResponse of(CategoryType categoryType, long amount) {
        return new ExpenditureAmountByCategoryResponse(
                categoryType,
                amount
        );
    }
}
