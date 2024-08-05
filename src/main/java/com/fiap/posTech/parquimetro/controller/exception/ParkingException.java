package com.fiap.posTech.parquimetro.controller.exception;

import org.springframework.http.HttpStatus;

public class ParkingException extends CustomException{
    public ParkingException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value(), "Bad Request");
    }
}
