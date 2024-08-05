package com.fiap.posTech.parquimetro.controller.exception;

import org.springframework.http.HttpStatus;

public class ControllerNotFoundException extends CustomException {
    public ControllerNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND.value(), "Not Found");
    }

}
