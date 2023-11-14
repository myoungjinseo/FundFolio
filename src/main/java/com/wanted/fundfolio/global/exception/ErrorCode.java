package com.wanted.fundfolio.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NON_EXISTENT_MEMBER(HttpStatus.BAD_REQUEST, "존재하지 않는 멤버입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
