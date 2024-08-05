package com.fiap.posTech.parquimetro.controller.exception;

import java.time.LocalDateTime;

public class CustomException extends RuntimeException {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;

    public CustomException(String message, int status, String error) {
        super(message);
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
