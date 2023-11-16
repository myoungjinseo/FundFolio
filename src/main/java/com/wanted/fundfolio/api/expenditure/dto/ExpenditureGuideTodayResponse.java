package com.wanted.fundfolio.api.expenditure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExpenditureGuideTodayResponse {
    private long todayAmount;
    private List<ExpenditureAmountResponse> monthByCategoryAmount;

    public static ExpenditureGuideTodayResponse of(long todayAmount,List<ExpenditureAmountResponse> monthByCategoryAmount){
        return new ExpenditureGuideTodayResponse(todayAmount,monthByCategoryAmount);
    }

}
