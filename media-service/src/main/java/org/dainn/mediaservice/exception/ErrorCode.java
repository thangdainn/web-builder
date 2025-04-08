package org.dainn.mediaservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    MEDIA_EXISTED("Media existed", HttpStatus.BAD_REQUEST),
    MEDIA_NOT_EXISTED("Media not existed", HttpStatus.NOT_FOUND),
    USER_NOT_AUTHENTICATED("User not authenticated", HttpStatus.UNAUTHORIZED),;

    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    private final String message;
    private final HttpStatusCode statusCode;
}
