package com.example.chattproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Already exists")
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
