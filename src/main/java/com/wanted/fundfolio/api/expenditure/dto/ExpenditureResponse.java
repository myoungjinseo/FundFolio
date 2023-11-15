package com.wanted.fundfolio.api.expenditure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.fundfolio.domain.category.entity.CategoryType;
import com.wanted.fundfolio.domain.expenditure.entity.Expenditure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ExpenditureResponse {
    private Long amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String content;

    private CategoryType categoryType;
    private boolean excludeTotal;

    public static ExpenditureResponse of(Expenditure expenditure){
        return new ExpenditureResponse(expenditure.getAmount(),expenditure.getDate(),expenditure.getContent()
                ,expenditure.getCategory().getCategoryType(), expenditure.isExcludeTotal());
    }
}
