package com.wanted.fundfolio.api.expenditure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.fundfolio.domain.category.entity.CategoryType;
import lombok.Getter;
import java.time.LocalDate;
import java.time.YearMonth;

@Getter
public class ExpenditureReadRequest {

    private long minAmount;
    private long maxAmount;

    private CategoryType categoryType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    public ExpenditureReadRequest(long minAmount, long maxAmount, CategoryType categoryType, LocalDate startDate, LocalDate endDate) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.categoryType = categoryType;
        this.startDate = startDate == null ? YearMonth.now().atDay(1) : startDate;
        this.endDate = endDate == null ? YearMonth.now().atEndOfMonth() : endDate;
    }
}
