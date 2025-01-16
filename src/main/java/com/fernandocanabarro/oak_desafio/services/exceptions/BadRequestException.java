package com.fernandocanabarro.oak_desafio.services.exceptions;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String msg){
        super(msg);
    }
}
