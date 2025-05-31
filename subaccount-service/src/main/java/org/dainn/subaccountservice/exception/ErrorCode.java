package org.dainn.subaccountservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    SA_NOT_EXISTED("SubAccount not existed", HttpStatus.NOT_FOUND),

    EMAIL_EXISTED("Email existed", HttpStatus.BAD_REQUEST),
    CONTACT_NOT_EXISTED("Contact not existed", HttpStatus.NOT_FOUND),

    USER_NOT_AGENCY_OWNER("User is not owner or not exists agency", HttpStatus.FORBIDDEN),
    SA_LIMIT("Tier just create maximum 3 subaccount", HttpStatus.CONFLICT),;


    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    private final String message;
    private final HttpStatusCode statusCode;
}
