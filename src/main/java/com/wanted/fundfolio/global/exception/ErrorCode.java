package com.wanted.fundfolio.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NON_EXISTENT_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 멤버입니다."),
    NON_EXISTENT_EXPENDITURE(HttpStatus.NOT_FOUND, "존재하지 않는 지출입니다."),
    Authorization_MEMBER(HttpStatus.FORBIDDEN, "권한이 없는 멤버입니다.");   ;

    private final HttpStatus httpStatus;
    private final String message;
}
