package com.wanted.fundfolio.api.expenditure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ComparedResponse {
    private long amountAll;
    private List<ExpenditureAmountByCategoryResponse> categoryResponses;

    public static ComparedResponse of(long amountAll, List<ExpenditureAmountByCategoryResponse> categoryResponses){
        return new ComparedResponse(amountAll,categoryResponses);
    }
}
