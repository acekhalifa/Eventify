package com.eventify.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponse(int status, String message, LocalDateTime timestamp, String path) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", path='" + path + '\'' +
                '}';
    }
}
class ValidationErrorResponse extends ErrorResponse{
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
                "fieldErrors=" + fieldErrors +
                '}';
    }
}