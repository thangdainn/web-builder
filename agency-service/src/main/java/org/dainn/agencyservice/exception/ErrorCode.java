package org.dainn.agencyservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    AGENCY_NOT_EXISTED("Agency not existed", HttpStatus.NOT_FOUND),
    USER_NOT_PERMISSION("User not permission", HttpStatus.FORBIDDEN);

    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    private final String message;
    private final HttpStatusCode statusCode;
}
