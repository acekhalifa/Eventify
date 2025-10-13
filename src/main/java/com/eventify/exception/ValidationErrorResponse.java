package com.eventify.exception;

import java.time.LocalDateTime;
import java.util.Map;

class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> fieldErrors;

    public ValidationErrorResponse(int status, String message, LocalDateTime timestamp, String path, Map<String, String> fieldErrors) {
        super(status, message, timestamp, path);
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    @Override
    public String toString() {
        return "ValidationErrorResponse{" +
                "status=" + getStatus() +
                ", message='" + getMessage() + '\'' +
                ", timestamp=" + getTimestamp() +
                ", path='" + getPath() + '\'' +
                "fieldErrors=" + fieldErrors +
                '}';
    }
}
