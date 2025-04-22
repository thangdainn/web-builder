package org.dainn.searchservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED("User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("Email not existed", HttpStatus.NOT_FOUND),

    INVITATION_EXISTED("Invitation existed", HttpStatus.BAD_REQUEST),
    INVITATION_NOT_EXISTED("Invitation not existed", HttpStatus.NOT_FOUND),

    PERMISSION_EXISTED("Permission existed", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED("Permission not existed", HttpStatus.NOT_FOUND),;

    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    private final String message;
    private final HttpStatusCode statusCode;
}
