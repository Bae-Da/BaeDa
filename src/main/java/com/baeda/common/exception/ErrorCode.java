package com.baeda.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(404, "User not found"),
    INVALID_TOKEN(401, "Invalid token"),
    TOKEN_EXPIRED(401, "Token has expired"),
    TOKEN_CREATION_ERROR(500, "Error occurred while creating token");

    private final int status;
    private final String message;
}
