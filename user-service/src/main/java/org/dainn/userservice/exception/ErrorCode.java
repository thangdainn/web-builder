package org.dainn.userservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED("User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("User not existed", HttpStatus.NOT_FOUND),

    INVITATION_EXISTED("Invitation existed", HttpStatus.BAD_REQUEST),
    INVITATION_NOT_EXISTED("Invitation not existed", HttpStatus.NOT_FOUND),

    PERMISSION_EXISTED("Permission existed", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED("Permission not existed", HttpStatus.NOT_FOUND),
    SEND_MAIL_FAIL("Fail send email", HttpStatus.EXPECTATION_FAILED),;

    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    private final String message;
    private final HttpStatusCode statusCode;
}
