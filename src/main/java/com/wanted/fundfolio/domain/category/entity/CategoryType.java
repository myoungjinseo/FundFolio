package com.wanted.fundfolio.domain.category.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CategoryType {
    FOOD_EXPENSE("식비"),
    TRAFFIC_EXPENSE("교통비");


    @JsonValue
    private final String koreaType;

    CategoryType(String koreaType) {
        this.koreaType = koreaType;
    }
}
