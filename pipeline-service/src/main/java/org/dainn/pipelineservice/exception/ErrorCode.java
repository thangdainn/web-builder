package org.dainn.pipelineservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    PIPELINE_NOT_EXISTED("Pipeline not existed", HttpStatus.NOT_FOUND),

    LANE_NOT_EXISTED("Lane not existed", HttpStatus.NOT_FOUND),

    TICKET_NOT_EXISTED("Ticket not existed", HttpStatus.NOT_FOUND),

    TAG_NOT_EXISTED("Tag not existed", HttpStatus.NOT_FOUND);



    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    private final String message;
    private final HttpStatusCode statusCode;
}
