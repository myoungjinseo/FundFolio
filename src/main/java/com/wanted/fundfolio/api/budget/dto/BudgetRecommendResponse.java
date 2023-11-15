package com.wanted.fundfolio.api.budget.dto;

import com.wanted.fundfolio.domain.category.entity.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BudgetRecommendResponse {
    private CategoryType categoryType;
    private long amountPercent;

    public static BudgetRecommendResponse of(CategoryType categoryType, long amountPercent) {
        return new BudgetRecommendResponse(
                categoryType,
                amountPercent
        );
    }
}
