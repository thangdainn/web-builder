package org.dainn.userservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED("User existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED("Email existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("User not existed", HttpStatus.NOT_FOUND),
    AGENCY_NOT_EXIST("Agency not existed", HttpStatus.NOT_FOUND),

    INVITATION_EXISTED("Invitation existed", HttpStatus.BAD_REQUEST),
    INVITATION_NOT_EXISTED("Invitation not existed", HttpStatus.NOT_FOUND),

    PERMISSION_EXISTED("Permission existed", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED("Permission not existed", HttpStatus.NOT_FOUND),
    SEND_MAIL_FAIL("Fail send email", HttpStatus.EXPECTATION_FAILED),
    INTERNAL_SERVER_ERROR("Error server", HttpStatus.INTERNAL_SERVER_ERROR),
    TEAM_MEMBER_LIMIT("Tier just create maximum 2 team member", HttpStatus.CONFLICT),
    SA_NOT_EXISTED("SubAccount not existed", HttpStatus.NOT_FOUND),

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
