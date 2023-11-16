package com.wanted.fundfolio.api.expenditure.dto;

import com.wanted.fundfolio.domain.category.entity.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenditureAmountResponse {

    private CategoryType categoryType;
    private long appropriateAmount;
    private long ExpenditureAmount;
    private long dangerPercent;

    public static ExpenditureAmountResponse of(CategoryType categoryType, long appropriateAmount,
                                            long expenditureAmount, long dangerPercent){
        return new ExpenditureAmountResponse(categoryType,appropriateAmount,expenditureAmount,dangerPercent);
    }


}
