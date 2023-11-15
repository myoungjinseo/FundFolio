package com.wanted.fundfolio.api.expenditure.dto;

import com.wanted.fundfolio.domain.category.entity.CategoryType;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class ExpenditureReadResponse {

    private Long ExpenditureId;

    private long amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private CategoryType categoryType;

    private boolean excludeTotal;
}
