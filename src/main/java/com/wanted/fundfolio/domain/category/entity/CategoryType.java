package com.wanted.fundfolio.domain.category.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CategoryType {
    FOOD_EXPENSE("식비"),
    TRAFFIC_EXPENSE("교통비"),
    HOUSE_EXPENSE("주거비"),
    PHONE_EXPENSE("통신비"),
    SHOP_EXPENSE("쇼핑비"),
    LEISURE_EXPENSE("여가비"),
    MEDICAL_EXPENSE("의료비"),
    OTHER("기타");


    @JsonValue
    private final String koreaType;

    CategoryType(String koreaType) {
        this.koreaType = koreaType;
    }
}
