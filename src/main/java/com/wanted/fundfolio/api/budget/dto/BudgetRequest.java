package com.wanted.fundfolio.api.budget.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.fundfolio.domain.category.entity.CategoryType;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.YearMonth;

@Getter
@ToString
public class BudgetRequest {
    private String amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "Asia/Seoul")
    private YearMonth date;
    private CategoryType categoryType;


}
