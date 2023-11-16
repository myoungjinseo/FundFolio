package com.wanted.fundfolio.api.expenditure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.fundfolio.domain.category.entity.CategoryType;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.time.YearMonth;

@Getter
public class ExpenditureReadRequest {

    private Long minAmount;
    private Long maxAmount;

    private CategoryType categoryType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    public ExpenditureReadRequest(Long minAmount, Long maxAmount, CategoryType categoryType, LocalDate startDate, LocalDate endDate) {
        this.minAmount = minAmount == null ? 0 : minAmount;
        this.maxAmount = maxAmount == null ? 2147483647 : maxAmount;
        this.categoryType = categoryType;
        this.startDate = startDate == null ? YearMonth.now().atDay(1) : startDate;
        this.endDate = endDate == null ? YearMonth.now().atEndOfMonth() : endDate;
    }
}
