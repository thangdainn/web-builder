package org.dainn.funnelservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    FUNNEL_NOT_EXISTED("Funnel not existed", HttpStatus.NOT_FOUND),
    FUNNEL_PAGE_NOT_EXISTED("Funnel page not existed", HttpStatus.NOT_FOUND);

    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    private final String message;
    private final HttpStatusCode statusCode;
}
