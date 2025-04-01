package org.dainn.subaccountservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    SA_NOT_EXISTED("SubAccount not existed", HttpStatus.NOT_FOUND),

    EMAIL_EXISTED("Email existed", HttpStatus.BAD_REQUEST),
    CONTACT_NOT_EXISTED("Contact not existed", HttpStatus.NOT_FOUND);


    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    private final String message;
    private final HttpStatusCode statusCode;
}
