package org.dainn.subscriptionservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    ADDONS_EXISTED("AddOns existed", HttpStatus.BAD_REQUEST),
    ADDONS_NOT_EXISTED("AddOns not existed", HttpStatus.NOT_FOUND),

    SUBSCRIPTION_EXISTED("Subscription existed", HttpStatus.BAD_REQUEST),
    SUBSCRIPTION_NOT_EXISTED("Subscription not existed", HttpStatus.NOT_FOUND);

    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    private final String message;
    private final HttpStatusCode statusCode;
}
