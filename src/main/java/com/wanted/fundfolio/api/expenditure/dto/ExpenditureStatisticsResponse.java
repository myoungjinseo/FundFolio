package com.wanted.fundfolio.api.expenditure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenditureStatisticsResponse {
    private ComparedResponse monthResponse;
    private ComparedResponse dayResponse;
    private ComparedResponse userResponse;

    public static ExpenditureStatisticsResponse of(ComparedResponse res1 , ComparedResponse res2, ComparedResponse res3){
        return new ExpenditureStatisticsResponse(res1,res2,res3);
    }
}
