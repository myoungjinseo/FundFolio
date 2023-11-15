package com.wanted.fundfolio.api.expenditure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExpenditureReadListResponse {

    private List<ExpenditureReadResponse> list;
    private long amountAll;
    private long amountByCategory;

    public static ExpenditureReadListResponse of(List<ExpenditureReadResponse> list, long amountAll, long amountByCategory){
        return new ExpenditureReadListResponse(list,amountAll,amountByCategory);
    }
}
