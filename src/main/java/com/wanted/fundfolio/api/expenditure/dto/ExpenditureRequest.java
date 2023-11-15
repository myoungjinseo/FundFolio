package com.wanted.fundfolio.api.expenditure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.fundfolio.domain.category.entity.CategoryType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ExpenditureRequest {
    private Long amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    private String content;

    private CategoryType categoryType;
}
